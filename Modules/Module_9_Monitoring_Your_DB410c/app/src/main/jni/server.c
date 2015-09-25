#include <errno.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>
#include <android/log.h>
#include "server.h"

#define TAG "ServerDEBUG"

    /**
     * Name:        setEnv
     * Description: Saves the environment.
     *
     * @param env1  Java environment
     */
void setEnv(JNIEnv* env1) {
    env = env1;
}

    /**
     * Name:        setJClassObject
     * Description: Saves the java object.
     *
     * @param jobj1 Java object
     */
void setJClassObject(jobject jobj1) {
    jobj = jobj1;
}


    /**
     * Name:        processMessage
     * Description: Process the message, in this case received from an HTTP Request.
     *
     * @param input Message to be processed.
     */
char* processMessage(const char* input) {
    jclass cls = (*env)->GetObjectClass(env,jobj);
    __android_log_print(ANDROID_LOG_ERROR, TAG, input);

    jmethodID mid = (*env)->GetMethodID(env,cls,"processMessage","(Ljava/lang/String;)Ljava/lang/String;");

    if (!cls || !mid) {
        exit(1);
    }

     jstring jmessage = (*env)->NewStringUTF(env, input);

    jstring message = (jstring)((*env)->CallObjectMethod(env, jobj, mid, jmessage));

    return (char *) (*env)->GetStringUTFChars(env,message,0);
}


    /**
     * Name:        beginServer
     * Description: Binds a socket and begins listening to it for the web server.
     *
     * @param ipaddress  IP Address
     * @param port       Port Number
     */
void beginServer(const char* ipaddress, const char * port) {

    //initialize the socket
    int sock = initializeSocket(ipaddress, port);

    if(sock < 0) {
        endServer(sock, NULL, 0);
    }

    runServer(sock);
}


    /**
     * Name:        initializeSocket
     * Description: Binds a socket and begins listening to it for the web server.
     *
     * @param ipaddress  IP Address
     * @param port       Port Number
     */
int initializeSocket(const char * ipaddress, const char * port) {

    /* sets up struct to contain critical ip info*/
    struct addrinfo hints;
    struct addrinfo * serverinfo;

    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_INET;
    hints.ai_socktype = SOCK_STREAM;

    //Gets the necessary information such as ipaddress/port
    if (getaddrinfo(ipaddress, port, &hints, &serverinfo) != 0) {
        __android_log_print(ANDROID_LOG_ERROR,TAG,"Failed to get server info", 1);
        __android_log_print(ANDROID_LOG_ERROR,TAG,"%s %s", ipaddress,port);
        exit(1);
    }

    __android_log_print(ANDROID_LOG_INFO,TAG,"Creating Socket");

    //initialize socket
    int sock = socket(serverinfo->ai_family, serverinfo->ai_socktype, serverinfo->ai_protocol);
    if (sock < 0) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "Socket initialization failed", 1);
        __android_log_print(ANDROID_LOG_ERROR, TAG, strerror(errno), 1);
        endServer(sock, serverinfo, 0);
    }

    int yes =1;
    //Rebind socket if in use
    if (setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "setsockopt");
        exit(1);
    }

    __android_log_print(ANDROID_LOG_INFO,TAG,"Binding Socket");

    //Bind Socket to ip address
    if (bind(sock, serverinfo->ai_addr,serverinfo->ai_addrlen) < 0) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "Socket Binding Failed", 1);
        endServer(sock, serverinfo, 0);
    }

    __android_log_print(ANDROID_LOG_INFO,TAG,"Listening to socket");
    //Listen to 10 requests at a time
    if (listen(sock, 10) < 0) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "Socket listening failed", 1);
        endServer(sock, serverinfo, 0);
    }

    //release memory
    freeaddrinfo(serverinfo);
    return sock;
}

    /**
     * Name:        endServer
     * Description: Closes server if error arises.
     *
     * @param socket    Socket File Descriptor for listening connection
     * @param addrinfo  Information on the server
     * @param newSock   Socket File Descriptor for accepted connection
     */
void endServer(int socket, struct addrinfo *serverinfo, int newSock) {
    if (socket > 0) {
        close(socket);
    }

    if (!serverinfo) {
        freeaddrinfo(serverinfo);
    }

    if (newSock > 0) {
        close(newSock);
    }
    exit(1);
}


    /**
     * Name:        runServer
     * Description: Runs the server. If a connection is accepted, then parent process continues listening
     *              and spawns child process to process the message and close the connection to client.
     *
     * @param sock  Socket File Descriptor for listening connection
     */
void runServer(int sock) {
    struct sockaddr_storage clientaddr;

    //continuous loop
    while (1) {

        socklen_t addr_size = sizeof(clientaddr);
        //accept the new connection
        int newsocket = accept(sock, (struct sockaddr*) &clientaddr, &addr_size);
        __android_log_print(ANDROID_LOG_INFO, TAG, "Accepting Socket");

        if (newsocket < 0) {
            __android_log_print(ANDROID_LOG_ERROR, TAG, "Socket accepting failed", 1);
            __android_log_print(ANDROID_LOG_INFO, TAG, strerror(errno));
            endServer(sock, NULL, newsocket);
            continue;
        }

        //split between parent process and child process. Parent=listening child=accepting
        if (!fork()) {
            close(sock);
            char buffer[HTTP_MAX_SIZE];
            __android_log_print(ANDROID_LOG_INFO, TAG, "Receiving");

            //grab info into httpReceive
            httpReceive(newsocket,buffer);
            __android_log_print(ANDROID_LOG_INFO,TAG,"%s %d", buffer, (int) strlen(buffer));

            //kick back message up to java
            const char * message = processMessage(buffer);
            __android_log_print(ANDROID_LOG_INFO,TAG,"%s %d", message, (int) strlen(message));
            __android_log_print(ANDROID_LOG_INFO, TAG, "Sending");

            //get the message and send it to client
            if (send(newsocket,message,strlen(message), 0) < 0){
                __android_log_print(ANDROID_LOG_ERROR,TAG,"Send Failed",1);
                endServer(-1, NULL, newsocket);
            }

            __android_log_print(ANDROID_LOG_INFO,TAG,"Sent Message %s", message);
            //close the new socket and exit the process
            close(newsocket);
            exit(0);
         }
        close(newsocket);
    }
}


    /**
     * Name:        httpReceive
     * Description: Receive an HTTP Request. Only extracts the first line.
     *
     * @param sock  Socket File Descriptor for listening connection
     * @param buffer Buffer containing the http request
    */
void httpReceive(int sock, char * buffer){
    char character = 0;
    int index = 0;
    while (1) {
        int numberReceived = recv(sock,&character,1,0);
        if (numberReceived > 0) {
           //stop loop if /r/n is detected!
            if (character=='\r') {
               int numberReceived = recv(sock,&character,1,MSG_PEEK);
               if (numberReceived>0 && character=='\n') {
                   buffer[index++]='\r';
                   buffer[index++]='\n';
                   break;
               }
           } else {
               buffer[index++]=character;
           }
        }
    }
    buffer[index] = '\0';
}
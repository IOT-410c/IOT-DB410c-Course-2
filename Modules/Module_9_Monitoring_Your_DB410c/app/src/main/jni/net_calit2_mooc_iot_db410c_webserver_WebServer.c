#include "net_calit2_mooc_iot_db410c_webserver_WebServer.h"
#include "server.h"

#define TAG "Interface"

/*
 * Class:     net_calit2_mooc_iot_db410c_webserver_WebServer
 * Method:    beginServer
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_net_calit2_mooc_iot_1db410c_webserver_WebServer_beginServer
        (JNIEnv * env, jobject obj, jstring ipaddress, jstring port){
    //grab ip and port strings
    const char * ip = (*env)->GetStringUTFChars(env,ipaddress,0);
    const char * pt = (*env)->GetStringUTFChars(env,port,0);

    //save class object and env to call later for callback
    setJClassObject(obj);
    setEnv(env);

    //start server code!
    beginServer(ip,pt);
    (*env)->ReleaseStringUTFChars(env,ipaddress,ip);
    (*env)->ReleaseStringUTFChars(env,port,pt);
}



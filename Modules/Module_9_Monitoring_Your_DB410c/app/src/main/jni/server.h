#include "net_calit2_mooc_iot_db410c_webserver_WebServer.h"
#define HTTP_MAX_SIZE 8*1024 //8kb

jobject jobj;
JNIEnv * env;

void beginServer(const char*, const char *);

void setJClassObject(jobject obj);

void setEnv(JNIEnv * env1);
#include <jni.h>
#include <stdlib.h>
#include <android/log.h>
#include <darkhttpd.h>
#include <jni_layer_Core.h>

#define LOGI(...) \
  ((void)__android_log_print(ANDROID_LOG_INFO, "core::", __VA_ARGS__))

char** stringArrToCharArr(JNIEnv *env, jsize len, jobjectArray strArray ){
    int i=0;
    jstring jstr;
    char **pstr = (char **) malloc(len * sizeof(char *));

    for (i=0 ; i < len; i++) {
       jstr = (*env)->GetObjectArrayElement(env, strArray, i);
       pstr[i] = (char *)(*env)->GetStringUTFChars(env, jstr, 0);
    }         
    return pstr;
}

JNIEXPORT jint JNICALL Java_jni_layer_Core_darkhttpd
  (JNIEnv *env, jclass jcl, jobjectArray com)
{
    int argc = (*env)->GetArrayLength(env, com);
    char **argv = stringArrToCharArr(env,(jsize) argc, com);
    return darkhttpd_main(argc,argv);
}

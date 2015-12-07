#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jlong JNICALL
        Java_com_kinpa200296_android_labs_samplendk_MainActivity_nativeGcd(JNIEnv *env, jclass type, jlong a, jlong b);

#ifdef _cplusplus
}
#endif
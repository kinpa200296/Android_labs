#include "gcd.h"

JNIEXPORT jlong JNICALL
               Java_com_kinpa200296_android_labs_samplendk_MainActivity_nativeGcd(JNIEnv *env, jclass type, jlong a, jlong b) {
    while (a > 0 && b > 0){
        if (a > b){
            a %= b;
        }
        else{
            b %= a;
        }
    }
    return a + b;
}

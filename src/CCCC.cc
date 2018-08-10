#include "CCCC.h"
#include <stdio.h>

JNIEXPORT jlong JNICALL Java_CCCC_getAddress(JNIEnv *, jclass, jobject obj) {
    return reinterpret_cast<jlong>(obj);
}
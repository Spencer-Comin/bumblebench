#include <jni.h>
#include <stdint.h>

static uint64_t obj1;
static uint64_t obj2;

JNIEXPORT jlong JNICALL
Java_net_adoptopenjdk_bumblebench_unsafe_Native_getOffset1(JNIEnv *env, jclass cls) {
	return (jlong)(&obj1);
}

JNIEXPORT jlong JNICALL
Java_net_adoptopenjdk_bumblebench_unsafe_Native_getOffset2(JNIEnv *env, jclass cls) {
	return (jlong)(&obj2);
}

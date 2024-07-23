#include <jni.h>
#include <stdint.h>
#include <stdio.h>

static uint8_t obj1[16];
static uint8_t obj2[16];

JNIEXPORT jlong JNICALL
Java_net_adoptopenjdk_bumblebench_unsafe_Native_getOffset1(JNIEnv *env, jclass cls) {
	return (jlong)(0);
}

JNIEXPORT jlong JNICALL
Java_net_adoptopenjdk_bumblebench_unsafe_Native_getOffset2(JNIEnv *env, jclass cls) {
	return (jlong)(0);
}

JNIEXPORT jobject JNICALL
Java_net_adoptopenjdk_bumblebench_unsafe_Native_getBase1(JNIEnv *env, jclass cls) {
	uint8_t *ptr = obj1;
	printf("From C: 16B allocated at 0x%p\n", ptr);
	return (jobject)(ptr);
}

JNIEXPORT jobject JNICALL
Java_net_adoptopenjdk_bumblebench_unsafe_Native_getBase2(JNIEnv *env, jclass cls) {
	uint8_t *ptr = obj2;
	printf("From C: 16B allocated at 0x%p\n", ptr);
	return (jobject)(ptr);
}
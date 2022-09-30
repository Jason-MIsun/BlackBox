//
// Created by Milk on 4/9/21.
//

#ifndef VIRTUALM_VMCORE_H
#define VIRTUALM_VMCORE_H

#include <jni.h>
#include <unistd.h>

#define VMCORE_CLASS "top/niunaijun/bcore/core/NativeCore"

class BoxCore {
public:
    static JavaVM *getJavaVM();
    static int getApiLevel();
    static int getCallingUid(int orig);
    static jstring redirectPathString(JNIEnv *env, jstring path);
    static jobject redirectPathFile(JNIEnv *env, jobject path);
    static void replaceFD(JNIEnv *env, jobject fd);
};


#endif //VIRTUALM_VMCORE_H

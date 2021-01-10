//
// Created by Kadir Mert Özcan on 18-Dec-20.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_kmozcan1_lyricquizapp_application_di_NetworkModule_getAPIKey(JNIEnv* env, jobject /* this */) {
    std::string api_key = "f5ade7368f3982deced830c89eed3eac";
    //f5ade7368f3982deced830c89eed3eac
    //4b4b9f8a9531a63279902dc768954473
    //89c1b077d558b3563a3eb810aa780622
    return env->NewStringUTF(api_key.c_str());
}

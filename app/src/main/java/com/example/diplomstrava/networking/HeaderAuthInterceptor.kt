package com.example.diplomstrava.networking

import okhttp3.Interceptor
import okhttp3.Response

class HeaderAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedRequest = originalRequest.newBuilder()
            //.addHeader("Authorization", "token ${Networking.accessToken}")
            .addHeader("Authorization", "Bearer ${Networking.accessToken}")
            .build()

        return chain.proceed(modifiedRequest)

    }
}
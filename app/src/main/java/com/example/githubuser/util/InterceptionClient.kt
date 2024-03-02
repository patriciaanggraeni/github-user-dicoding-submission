package com.example.githubuser.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient

object InterceptionClient {

    val interceptor = Interceptor {
        val request = it.request()
        val newRequest = request
            .newBuilder()
            .header("Authorization", "TOKEN")
            .build()

        it.proceed(newRequest)
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}
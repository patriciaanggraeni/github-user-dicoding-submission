package com.example.githubuser.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient

object InterceptionClient {

    val interceptor = Interceptor {
        val request = it.request()
        val newRequest = request
            .newBuilder()
            .header("Authorization", "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")
            .build()

        it.proceed(newRequest)
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}
package com.example.githubuser.data.services

import com.example.githubuser.util.InterceptionClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    private const val BASE_URL = "https://api.github.com/"

    val userApi: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(InterceptionClient.okHttpClient)
            .build()
            .create(UserApi::class.java)
    }
}
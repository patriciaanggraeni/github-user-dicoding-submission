package com.example.githubuser.data.services

import com.example.githubuser.data.remote.response.SearchResult
import com.example.githubuser.data.remote.response.UserDetail
import com.example.githubuser.domain.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("users")
    fun getAllUsers(): Call<List<User>>

    @GET("search/users")
    fun searchUsers(@Query("q") username: String): Call<SearchResult>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetail>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<User>>
}

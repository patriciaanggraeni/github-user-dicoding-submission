package com.example.githubuser.data.remote.repository

import com.example.githubuser.data.remote.response.SearchResult
import com.example.githubuser.data.remote.response.UserDetail
import com.example.githubuser.data.services.Api
import com.example.githubuser.domain.model.User
import com.example.githubuser.domain.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl: UserRepository {

    private val userApi = Api.userApi

    override fun getAllUsers(callback: (Result<List<User>>) -> Unit) {
        userApi.getAllUsers().enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    callback(Result.success((response.body()!!)))
                } else {
                    callback(Result.failure(RuntimeException("Failed to fetch data")))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    override fun getUserDetail(username: String, callback: (Result<UserDetail>) -> Unit) {
        userApi.getUserDetail(username).enqueue(object: Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                if (response.isSuccessful) {
                    callback(Result.success((response.body()!!)))
                } else {
                    callback(Result.failure(RuntimeException("Failed to fetch data")))
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    override fun searchUser(username: String, callback: (Result<SearchResult>) -> Unit) {
        userApi.searchUsers(username).enqueue(object: Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if (response.isSuccessful) {
                    callback(Result.success((response.body()!!)))
                } else {
                    callback(Result.failure(RuntimeException("Failed to fetch data")))
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    override fun getUserFollowers(username: String, callback: (Result<List<User>>) -> Unit) {
        userApi.getUserFollowers(username).enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    callback(Result.success((response.body()!!)))
                } else {
                    callback(Result.failure(RuntimeException("Failed to fetch data")))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }

    override fun getUserFollowing(username: String, callback: (Result<List<User>>) -> Unit) {
        userApi.getUserFollowing(username).enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    callback(Result.success((response.body()!!)))
                } else {
                    callback(Result.failure(RuntimeException("Failed to fetch data")))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
               callback(Result.failure(t))
            }
        })
    }
}
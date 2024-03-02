package com.example.githubuser.domain.repository

import com.example.githubuser.data.remote.response.SearchResult
import com.example.githubuser.data.remote.response.UserDetail
import com.example.githubuser.domain.model.User

interface UserRepository {

    fun getAllUsers(callback: (Result<List<User>>) -> Unit)
    fun getUserDetail(username: String, callback: (Result<UserDetail>) -> Unit)
    fun searchUser(username: String, callback: (Result<SearchResult>) -> Unit)
    fun getUserFollowers(username: String, callback: (Result<List<User>>) -> Unit)
    fun getUserFollowing(username: String, callback: (Result<List<User>>) -> Unit)

}
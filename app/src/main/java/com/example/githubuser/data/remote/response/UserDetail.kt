package com.example.githubuser.data.remote.response

data class UserDetail(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val name: String,
    val company: String?,
    val blog: String?,
    val location: String?,
    val bio: String?,
    val publicRepos: Int?,
    val followers: Int?,
    val following: Int?
)

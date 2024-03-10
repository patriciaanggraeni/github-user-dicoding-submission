package com.example.githubuser.data.remote.response
import com.example.githubuser.domain.model.User

data class SearchResult(
    val totalCount: Int?,
    val items: List<User>
)
package com.example.githubuser.data.remote.response

data class SearchResult(
    val totalCount: Int?,
    val items: List<User?>
)
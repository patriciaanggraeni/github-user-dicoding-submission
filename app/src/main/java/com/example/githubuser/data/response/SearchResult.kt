package com.example.githubuser.data.response

data class SearchResult(
    val totalCount: Int?,
    val items: List<User?>
)
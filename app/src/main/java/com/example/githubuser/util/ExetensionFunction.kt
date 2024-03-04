package com.example.githubuser.util

fun String.capitilizeFirst(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase()
        else it.toString()
    }
}
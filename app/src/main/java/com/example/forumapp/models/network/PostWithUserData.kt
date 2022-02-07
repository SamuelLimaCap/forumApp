package com.example.forumapp.models.network

data class PostWithUserData(
    val body: String,
    val id: Int,
    val title: String,
    val user: User,
    val userId: Int
)
package com.example.forumapp.models

import com.example.forumapp.models.network.PostWithUserData

data class PostWithCreatorName(
    val body: String,
    val id: Int,
    val title: String,
    val userCreatorName: String,
    val userId: Int
) {
    constructor(postWithUserData: PostWithUserData) : this(
        postWithUserData.body,
        postWithUserData.id,
        postWithUserData.title,
        postWithUserData.user.name,
        postWithUserData.userId
    )
}
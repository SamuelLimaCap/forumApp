package com.example.forumapp.network

import com.example.forumapp.network.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {

    @GET("/posts")
    suspend fun getAll() : Response<List<Post>>
}
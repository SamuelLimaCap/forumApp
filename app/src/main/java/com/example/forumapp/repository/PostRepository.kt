package com.example.forumapp.repository

import com.example.forumapp.network.PostApi
import com.example.forumapp.network.model.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostRepository {

    private val BASE_URL =  "https://jsonplaceholder.typicode.com/"

    val retrofit by lazy {
        Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
            .create(PostApi::class.java)
    }
}
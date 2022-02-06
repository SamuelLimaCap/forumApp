package com.example.forumapp.repository

import com.example.forumapp.network.PostApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostRepository {

    private const val BASE_URL =  "https://jsonplaceholder.typicode.com/"

    val retrofit: PostApi by lazy {
        Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
            .create(PostApi::class.java)
    }
}
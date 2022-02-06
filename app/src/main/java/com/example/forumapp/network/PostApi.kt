package com.example.forumapp.network

import com.example.forumapp.network.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {

    /*
    BASE_URL =  "https://jsonplaceholder.typicode.com/"
    ROUTES: https://github.com/typicode/json-server#routes
     */

    @GET("/posts")
    suspend fun getAll() : Response<List<Post>>

    @GET("/posts")
    suspend fun getGroup10(@Query("_page") int: Int): Response<List<Post>>
}
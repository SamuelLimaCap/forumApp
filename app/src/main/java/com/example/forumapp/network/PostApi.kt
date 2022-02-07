package com.example.forumapp.network

import com.example.forumapp.models.Comment
import com.example.forumapp.models.Post
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

    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") postId: Int): Response<Post>

    @GET("/posts/{id}/comments")
    suspend fun getCommentsById(@Path("id") postId: Int) : Response<List<Comment>>

    @GET("/posts/{id}/comments")
    suspend fun getGroup10CommentsById(@Path("id") postId: Int, @Query("_page") page: Int) : Response<List<Comment>>


}
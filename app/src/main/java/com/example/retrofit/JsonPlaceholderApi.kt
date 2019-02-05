package com.example.retrofit

import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceholderApi{

    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Call<List<Comments>>

    @GET("posts")
    fun getPosts(@Query("userId") userId: Int): Call<List<Post>>

    @GET("posts")
    fun getPosts(@Query("userId") userId: Int,
                 @Query("_sort") sort: String,
                 @Query("_order") order: String): Call<List<Post>>

    @POST("posts")
    fun createPost(@Body post: Post): Call<Post>
}
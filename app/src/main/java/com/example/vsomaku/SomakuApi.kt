package com.example.vsomaku

import com.example.vsomaku.data.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SomakuApi {
    @GET("posts")
    fun getPosts() : Call<List<Post>>

    @GET("comments")
    fun getComments(@Query("postId") postId : Int) : Call<List<Comment>>

    @GET("users")
    fun getUser(@Query("id") userId : Int) : Call<List<User>>

    @GET("albums")
    fun getAlbums(@Query("userId") userId : Int) : Call<List<Album>>

    @GET("photos")
    fun getPhotos(@Query("albumId") albumId : Int) : Call<List<Photo>>
}
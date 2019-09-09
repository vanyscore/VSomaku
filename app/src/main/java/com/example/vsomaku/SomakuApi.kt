package com.example.vsomaku

import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
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

    companion object {
        private const val BASE_URL = "http://www.somaku.com/"

        fun create() : SomakuApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(SomakuApi::class.java)
        }
    }
}
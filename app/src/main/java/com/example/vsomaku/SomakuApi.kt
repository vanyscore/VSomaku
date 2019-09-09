package com.example.vsomaku

import com.example.vsomaku.data.Post
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface SomakuApi {
    @GET("posts")
    fun getPosts() : Call<List<Post>>

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
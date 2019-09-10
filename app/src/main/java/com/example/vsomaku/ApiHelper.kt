package com.example.vsomaku

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    private const val BASE_URL = "http://www.somaku.com/"

    val apiInstance: SomakuApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(SomakuApi::class.java)
}
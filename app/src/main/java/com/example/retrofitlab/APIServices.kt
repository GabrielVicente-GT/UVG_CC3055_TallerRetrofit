package com.example.retrofitlab

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServices {

    @GET("top-headlines")
    suspend fun getNewsByCategory(
        @Query("country") country:String,
        @Query("category") category:String,
        @Query("apiKey") apiKey:String
        ): Response<NewsResponse>
}
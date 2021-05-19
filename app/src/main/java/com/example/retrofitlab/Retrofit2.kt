package com.example.retrofitlab

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit2 {

    private var urls: APIServices? = null

    init {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        urls = retrofit.create(APIServices::class.java)
    }

    fun getService(): APIServices?{
        return urls
    }

}
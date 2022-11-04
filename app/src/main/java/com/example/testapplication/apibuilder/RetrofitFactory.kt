package com.example.testapplication.apibuilder

import com.example.testapplication.network.remotedatasource.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {


    fun create() : Api {
        return Retrofit.Builder()
            .baseUrl("https://apitest.virta.fi/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build())
            .build()
            .create(Api::class.java)
    }

}
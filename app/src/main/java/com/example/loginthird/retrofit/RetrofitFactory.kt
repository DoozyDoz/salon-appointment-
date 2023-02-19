package com.example.loginthird.retrofit

import com.example.loginthird.ApiConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitFactory {
    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_ENDPOINT)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun createConnectionService(): ConnectionService {
        return retrofit.create(ConnectionService::class.java)
    }
}

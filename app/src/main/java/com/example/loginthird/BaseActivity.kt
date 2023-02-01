package com.example.loginthird

import androidx.appcompat.app.AppCompatActivity
import com.example.loginthird.api.interceptor.TokenInterceptor
import com.example.loginthird.retrofit.ConnectionService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class BaseActivity:AppCompatActivity() {
    fun createExceptionHandler(message: String): CoroutineExceptionHandler {
        return GlobalScope.createExceptionHandler(message) {
            toast(it.message!!)
        }
    }
    fun getApi(): ConnectionService {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(getOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ConnectionService::class.java)
    }
    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(this))
            .build()
    }
}
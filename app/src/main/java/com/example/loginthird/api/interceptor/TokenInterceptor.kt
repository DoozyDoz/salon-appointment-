package com.example.loginthird.api.interceptor

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.loginthird.baseConfig
import com.example.loginthird.helpers.BaseConfig
import okhttp3.Interceptor
import okhttp3.Response


class TokenInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val headers = response.headers
        for (name in headers.names()) {
            if (name.equals("accessToken", ignoreCase = true)) {
                val accessToken = headers[name]
                context.baseConfig.accessToken = accessToken!!
            } else if (name.equals("refreshToken", ignoreCase = true)) {
                val refreshToken = headers[name]
                context.baseConfig.refreshToken = refreshToken!!
            }
        }
        return response
    }
}
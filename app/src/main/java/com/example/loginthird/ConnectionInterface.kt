package com.example.loginthird

import okhttp3.Call
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ConnectionInterface {
    @POST("/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String): Call<ResponseBody>
}
package com.example.loginthird.retrofit

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ConnectionService {

    @POST("auth/login")
    suspend fun login( //https://stackoverflow.com/a/59769743/8872691
        @Body reqLogin: ReqLogin?
    ): ResponseLogin
}
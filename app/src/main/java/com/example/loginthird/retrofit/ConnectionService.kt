package com.example.loginthird.retrofit

import retrofit2.http.Body
import retrofit2.http.POST

interface ConnectionService {

    @POST("auth/login")
    suspend fun login( //https://stackoverflow.com/a/59769743/8872691
        @Body reqLogin: RequestLogin?
    ): ResponseLogin

    @POST("sessions")
    suspend fun createSession(
        @Body reqCreateSession: RequestCreateSession?
    ): ResponseCreateSession
}
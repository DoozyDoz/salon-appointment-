package com.example.loginthird.retrofit

import com.example.loginthird.models.ApiSession
import retrofit2.http.*

interface ConnectionService {

    @POST("auth/login")
    suspend fun login( //https://stackoverflow.com/a/59769743/8872691
        @Body reqLogin: RequestLogin?
    ): ResponseLogin

    @POST("sessions")
    suspend fun createSession(
        @Body reqCreateSession: RequestCreateSession?
    ): ResponseCreateSession

    @GET("sessions")
    suspend fun getSessions(
    ): ResponseAppointments

    @PATCH("sessions/{id}")
    suspend fun editSession(
        @Path("id") id: String,
        @Body apiSession: ApiSession
    ): ResponseCreateSession
}
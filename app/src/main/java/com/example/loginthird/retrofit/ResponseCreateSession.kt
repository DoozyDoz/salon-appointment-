package com.example.loginthird.retrofit

import com.example.loginthird.models.ApiSession
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseCreateSession(
    @field:Json(name = "msg") val message: String? = null,
    @field:Json(name = "session") val session: ApiSession? = null
)
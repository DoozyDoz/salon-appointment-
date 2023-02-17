package com.example.loginthird.retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseAppointments(
    @field:Json(name = "count") val count: Int? = null,
    @field:Json(name = "sessions") val sessions: List<ApiSession>? = null
)
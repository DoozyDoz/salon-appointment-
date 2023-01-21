package com.example.loginthird.retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReqLogin(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "password") var password: String,
)

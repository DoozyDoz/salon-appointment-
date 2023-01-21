package com.example.loginthird.retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseLogin(
    @field:Json(name = "msg") val message: String? = null,
    @field:Json(name = "user") val loggedInUser: ApiLoggedInUser? = null
)
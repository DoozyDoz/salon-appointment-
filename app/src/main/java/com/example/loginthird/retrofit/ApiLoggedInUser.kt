package com.example.loginthird.retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiLoggedInUser(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "userId") val userId: Int?,
    @field:Json(name = "role") val role: String?
)
package com.example.loginthird.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiSession(
    @field:Json(name = "sessionDate") val sessionDate: String?,
    @field:Json(name = "service") var service: String?,
    @field:Json(name = "barber") var barber: String?,
    @field:Json(name = "customerName") var customerName: String?,
    @field:Json(name = "customerContact") var customerContact: String?,
    @field:Json(name = "nextAppointmentDate") var nextAppointmentDate: String?,
    @field:Json(name = "dateOfBirth") var dateOfBirth: String?,
    @field:Json(name = "Location") var Location: String?,
    @field:Json(name = "status") var status: String?,
    @field:Json(name = "_id") var sessionId: String?,
    @field:Json(name = "createdAt") var creationDate: String?,
    @field:Json(name = "updatedAt") var updateDate: String?,
    )
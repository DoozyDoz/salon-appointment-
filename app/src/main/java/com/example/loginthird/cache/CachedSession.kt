package com.example.loginthird.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class CachedSession(
    @PrimaryKey val sessionId: String,
    val sessionDate: String?,
    var service: String?,
    var barber: String?,
    var customerName: String?,
    var customerContact: String?,
    var nextAppointmentDate: String?,
    var dateOfBirth: String?,
    var location: String?,
    var status: String?,
    var createdAt: String?,
    var updatedAt: String?
)
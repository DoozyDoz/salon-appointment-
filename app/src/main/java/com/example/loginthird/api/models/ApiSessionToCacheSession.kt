package com.example.loginthird.api.models

import com.example.loginthird.cache.CachedSession
import com.example.loginthird.models.ApiSession
import com.example.loginthird.models.mappers.MappingException

fun ApiSessionToCacheSession(apiSession: ApiSession): CachedSession {
    return CachedSession(
        sessionId = apiSession.sessionId ?: throw MappingException("Session ID cannot be null"),
        sessionDate = apiSession.sessionDate,
        service = apiSession.service,
        barber = apiSession.barber,
        customerName = apiSession.customerName,
        customerContact = apiSession.customerContact,
        nextAppointmentDate = apiSession.nextAppointmentDate,
        dateOfBirth = apiSession.dateOfBirth,
        location = apiSession.Location,
        status = apiSession.status,
        createdAt = apiSession.creationDate,
        updatedAt = apiSession.updateDate
    )
}

fun CachedSessionToApiSession(cachedSession: CachedSession): ApiSession {
    return ApiSession(
        sessionId = cachedSession.sessionId,
        sessionDate = cachedSession.sessionDate,
        service = cachedSession.service,
        barber = cachedSession.barber,
        customerName = cachedSession.customerName,
        customerContact = cachedSession.customerContact,
        nextAppointmentDate = cachedSession.nextAppointmentDate,
        dateOfBirth = cachedSession.dateOfBirth,
        Location = cachedSession.location,
        status = cachedSession.status,
        creationDate = cachedSession.createdAt,
        updateDate = cachedSession.updatedAt
    )
}

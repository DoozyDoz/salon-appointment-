package com.example.loginthird.models.mappers

import com.example.loginthird.models.ApiSession
import com.example.loginthird.models.UISession


class ApiSessionMapper : ApiMapper<ApiSession, UISession> {
    override fun mapToDomain(apiEntity: ApiSession): UISession {
        return UISession(
            id = apiEntity.sessionId ?: throw MappingException("Session ID cannot be null"),
            title = apiEntity.nextAppointmentDate.orEmpty(),
            completed = apiEntity.status == "done"
        )
    }
}

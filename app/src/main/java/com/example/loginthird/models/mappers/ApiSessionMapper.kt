package com.example.loginthird.models.mappers

import com.example.loginthird.retrofit.ApiSession
import com.example.loginthird.ui.UISession


class ApiSessionMapper : ApiMapper<ApiSession, UISession> {
    override fun mapToDomain(apiEntity: ApiSession): UISession {
        return UISession(
            id = apiEntity.sessionId ?: throw MappingException("Session ID cannot be null"),
            title = apiEntity.sessionDate.orEmpty(),
            completed = apiEntity.status == "done"
        )
    }
}

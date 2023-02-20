package com.example.loginthird.cache.models

import com.example.loginthird.cache.CachedSession
import com.example.loginthird.models.UISession
import com.example.loginthird.models.mappers.ApiMapper


class CacheSessionMapper : ApiMapper<CachedSession, UISession> {
    override fun mapToDomain(cacheEntity: CachedSession): UISession {
        return UISession(
            id = cacheEntity.sessionId,
            title = cacheEntity.nextAppointmentDate.orEmpty(),
            completed = cacheEntity.status == "done"
        )
    }
}
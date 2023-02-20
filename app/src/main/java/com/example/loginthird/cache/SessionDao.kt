package com.example.loginthird.cache

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SessionDao {
    @Query("SELECT * FROM sessions")
    fun getSessions(): LiveData<List<CachedSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessions(sessions: List<CachedSession>)

    @Query("SELECT * FROM sessions WHERE sessionId = :id")
    fun getSessionById(id: String): CachedSession?

    @Update
    fun updateSession(session: CachedSession)
}
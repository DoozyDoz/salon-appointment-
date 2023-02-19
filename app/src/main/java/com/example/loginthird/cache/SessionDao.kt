package com.example.loginthird.cache

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SessionDao {
    @Query("SELECT * FROM sessions")
    fun getSessions(): LiveData<List<CachedSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessions(sessions: List<CachedSession>)
}
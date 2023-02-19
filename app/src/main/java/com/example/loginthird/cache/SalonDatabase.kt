package com.example.loginthird.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CachedSession::class], version = 1)
abstract class SalonDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile
        private var INSTANCE: SalonDatabase? = null

        fun getInstance(context: Context): SalonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SalonDatabase::class.java,
                    "session_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
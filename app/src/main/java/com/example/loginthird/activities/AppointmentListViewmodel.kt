package com.example.loginthird.activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.loginthird.api.models.ApiSessionToCacheSession
import com.example.loginthird.cache.SalonDatabase
import com.example.loginthird.cache.models.CacheSessionMapper
import com.example.loginthird.models.UISession
import com.example.loginthird.retrofit.RetrofitFactory


class AppointmentListViewmodel(application: Application) : AndroidViewModel(application) {

    private val sessionDao = SalonDatabase.getInstance(application).sessionDao()

    val sessions: LiveData<List<UISession>> =
        Transformations.map(sessionDao.getSessions()) { cachedSessions ->
            cachedSessions.map { cachedSession ->
                CacheSessionMapper().mapToDomain(cachedSession)
            }
        }

    suspend fun refreshSessions() {
        val apiSessions =
            RetrofitFactory.createConnectionService().getSessions().sessions

        val cachedSessionsEntities =
            apiSessions?.map { ApiSessionToCacheSession(it) }

        sessionDao.insertSessions(cachedSessionsEntities!!)
    }
}
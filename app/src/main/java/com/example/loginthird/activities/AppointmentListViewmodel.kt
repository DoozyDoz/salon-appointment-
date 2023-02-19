package com.example.loginthird.activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.loginthird.api.models.ApiSessionToCacheSession
import com.example.loginthird.cache.SalonDatabase
import com.example.loginthird.cache.models.CacheSessionMapper
import com.example.loginthird.models.UISession
import com.example.loginthird.retrofit.RetrofitFactory
import kotlinx.coroutines.launch


class AppointmentListViewmodel(application: Application) : AndroidViewModel(application) {

    private val sessionDao = SalonDatabase.getInstance(application).sessionDao()
    private val refreshInterval = 5 * 60 * 1000 // 5 minutes in milliseconds
    private var lastRefreshTime = 0L

    fun refreshSessionsIfNeeded() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastRefreshTime >= refreshInterval) {
            lastRefreshTime = currentTime
            refreshSessions()
        }
    }


    val sessions: LiveData<List<UISession>> =
        Transformations.map(sessionDao.getSessions()) { cachedSessions ->
            cachedSessions.map { cachedSession ->
                CacheSessionMapper().mapToDomain(cachedSession)
            }
        }

    fun refreshSessions() {
        viewModelScope.launch {
            try {
                val apiSessions = RetrofitFactory.createConnectionService().getSessions().sessions
                val cachedSessionsEntities = apiSessions?.map { ApiSessionToCacheSession(it) }
                sessionDao.insertSessions(cachedSessionsEntities!!)
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
}
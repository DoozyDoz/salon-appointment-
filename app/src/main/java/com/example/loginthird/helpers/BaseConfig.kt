package com.example.loginthird.helpers

import android.content.Context
import android.provider.UserDictionary.Words.APP_ID
import com.example.loginthird.ACCESS_TOKEN
import com.example.loginthird.APP_RUN_COUNT
import com.example.loginthird.REFRESH_TOKEN
import com.example.loginthird.getSharedPrefs

open class BaseConfig(val context: Context) {
    protected val prefs = context.getSharedPrefs()

    companion object {
        fun newInstance(context: Context) = BaseConfig(context)
    }

    var appRunCount: Int
        get() = prefs.getInt(APP_RUN_COUNT, 0)
        set(appRunCount) = prefs.edit().putInt(APP_RUN_COUNT, appRunCount).apply()

    var appId: String
        get() = prefs.getString(APP_ID, "")!!
        set(appId) = prefs.edit().putString(APP_ID, appId).apply()

    var accessToken: String
        get() = prefs.getString(ACCESS_TOKEN, "")!!
        set(accessToken) = prefs.edit().putString(ACCESS_TOKEN, accessToken).apply()

    var refreshToken: String
        get() = prefs.getString(REFRESH_TOKEN, "")!!
        set(refreshToken) = prefs.edit().putString(REFRESH_TOKEN, refreshToken).apply()
}
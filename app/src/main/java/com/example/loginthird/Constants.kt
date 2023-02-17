package com.example.loginthird

import android.os.Looper

fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun ensureBackgroundThread(callback: () -> Unit) {
    if (isOnMainThread()) {
        Thread {
            callback()
        }.start()
    } else {
        callback()
    }
}

object ApiConstants {
//    const val BASE_ENDPOINT = "http://192.168.43.202:5000/api/v1/"
//    const val BASE_ENDPOINT = "http://192.168.43.129:5000/api/v1/"
    const val BASE_ENDPOINT = "http://192.168.28.254:5000/api/v1/"
    const val AUTH_ENDPOINT = "oauth2/token/"
    const val ANIMALS_ENDPOINT = "animals"
    const val ANIMAL_ENDPOINT = "animal"

    const val KEY = "INSERT_YOUR_KEY_HERE"
    const val SECRET = "INSERT_YOUR_SECRET_HERE"
}

const val PREFS_KEY = "Prefs"
const val APP_RUN_COUNT = "app_run_count"
const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"
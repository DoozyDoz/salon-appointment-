package com.example.loginthird

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun CoroutineScope.createExceptionHandler(
    message: String,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
    Log.e(throwable.toString(), message)
    throwable.printStackTrace()
    launch {
        action(throwable)
    }
}
package com.example.loginthird

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.loginthird.helpers.BaseConfig
import com.example.loginthird.helpers.Config

fun Context.openActivity(activityClass: Class<out Activity>) {
    val intent = Intent(this, activityClass)
    startActivity(intent)
}

fun Context.toast(id: Int, length: Int = Toast.LENGTH_SHORT) {
    toast(getString(id), length)
}

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    try {
        if (isOnMainThread()) {
            doToast(this, msg, length)
        } else {
            Handler(Looper.getMainLooper()).post {
                doToast(this, msg, length)
            }
        }
    } catch (e: Exception) {
    }
}

private fun doToast(context: Context, message: String, length: Int) {
    if (context is Activity) {
        if (!context.isFinishing && !context.isDestroyed) {
            Toast.makeText(context, message, length).show()
        }
    } else {
        Toast.makeText(context, message, length).show()
    }
}


val Context.config: Config get() = Config.newInstance(applicationContext)
fun Context.getSharedPrefs() = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
val Context.baseConfig: BaseConfig get() = BaseConfig.newInstance(this)

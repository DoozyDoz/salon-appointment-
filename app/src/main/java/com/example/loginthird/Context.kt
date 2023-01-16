package com.example.loginthird

import android.app.Activity
import android.content.Context
import android.content.Intent

fun Context.openActivity(activityClass: Class<out Activity>) {
    val intent = Intent(this, activityClass)
    startActivity(intent)
}
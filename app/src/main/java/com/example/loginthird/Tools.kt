package com.example.loginthird

import android.app.Activity
import android.os.Build
import android.view.WindowManager

class Tools {

    companion object {
        fun setSystemBarColor(act: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = act.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = act.resources.getColor(R.color.colorPrimaryDark)
            }
        }
    }

}
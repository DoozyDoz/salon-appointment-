package com.example.loginthird

import android.os.Bundle
import com.example.loginthird.databinding.ActivitySplashBinding
import java.util.*

class SplashActivity: BaseActivity() {

    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var task: TimerTask = object : TimerTask() {
            override fun run() {
                openActivity(LoginActivity::class.java)
                finish()
            }
        }
        Timer().schedule(task, 600)

// for system bar in lollipop
        Tools.systemBarLolipop(this)
    }
}



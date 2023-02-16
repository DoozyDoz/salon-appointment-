package com.example.loginthird

import android.os.Bundle
import com.example.loginthird.databinding.ActivityAppointmentListBinding
import com.example.loginthird.databinding.ActivityLoginBinding
import com.example.loginthird.retrofit.ApiLoggedInUser
import com.example.loginthird.retrofit.RequestLogin
import com.example.loginthird.singleton.User
import kotlinx.coroutines.*

class AppointmentListActivity : BaseActivity() {

    private lateinit var binding: ActivityAppointmentListBinding
    private lateinit var email: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            signinUser {
                openActivity(DashboardActivity::class.java)
            }
        }
    }
}
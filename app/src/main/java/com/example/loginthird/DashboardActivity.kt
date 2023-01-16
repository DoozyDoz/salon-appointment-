package com.example.loginthird

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginthird.databinding.ActivityDashboardBinding
import com.example.loginthird.databinding.ActivityLoginBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCreateAppointment.setOnClickListener {
            openActivity(CreateAppointmentActivity::class.java)
        }
    }
}
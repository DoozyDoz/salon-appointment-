package com.example.loginthird

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginthird.databinding.ActivityCreateAppointmentBinding
import com.example.loginthird.databinding.ActivityDashboardBinding

class CreateAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
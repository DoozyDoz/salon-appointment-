package com.example.loginthird

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginthird.activities.AppointmentListActivity
import com.example.loginthird.databinding.ActivityDashboardBarbarBinding
import com.example.loginthird.databinding.ActivityDashboardBinding
import com.example.loginthird.databinding.ActivityLoginBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBarbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBarbarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        binding.buttonCreateAppointment.setOnClickListener {
            openActivity(CreateAppointmentActivity::class.java)
        }

        binding.buttonOpenAppointmentList.setOnClickListener {
            openActivity(AppointmentListActivity::class.java)
        }
    }

    private fun initToolbar() {
        val toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Dashboard"
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
    }
}
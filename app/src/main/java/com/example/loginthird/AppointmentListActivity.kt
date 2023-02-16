package com.example.loginthird

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginthird.adapter.SessionListAdapter
import com.example.loginthird.databinding.ActivityAppointmentListBinding
import com.example.loginthird.ui.UISession

class AppointmentListActivity : BaseActivity() {

    private lateinit var binding: ActivityAppointmentListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionList = findViewById<RecyclerView>(R.id.session_list)
        sessionList.layoutManager = LinearLayoutManager(this)

        val sessions = listOf(
            UISession("Session 1", false),
            UISession("Session 2", true),
            UISession("Session 3", false)
        )

        getSessionListFromAPI()

        val adapter = SessionListAdapter(sessions)
        sessionList.adapter = adapter
    }

    private fun getSessionListFromAPI() {

    }
}
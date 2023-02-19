package com.example.loginthird.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginthird.BaseActivity
import com.example.loginthird.R
import com.example.loginthird.adapter.SessionListAdapter
import com.example.loginthird.databinding.ActivityAppointmentListBinding
import com.example.loginthird.models.UISession
import com.example.loginthird.models.mappers.ApiSessionMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppointmentListActivity : BaseActivity() {

    private lateinit var binding: ActivityAppointmentListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppointmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionList = findViewById<RecyclerView>(R.id.session_list)
        sessionList.layoutManager = LinearLayoutManager(this)

        getSessionListFromAPI { sessions ->
            val adapter = SessionListAdapter(sessions)
            sessionList.adapter = adapter
        }


    }

    private fun getSessionListFromAPI(function: (List<UISession>) -> Unit) {
        val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")
        val api = getApi()


        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = api.getSessions()
            withContext(Dispatchers.Main) {
                if (response.sessions != null) {
                    val apiSessionMapper: ApiSessionMapper = ApiSessionMapper()
                    val sessions = response.sessions.map { apiSessionMapper.mapToDomain(it) }
                    function(sessions)
                } else {
//                    toast(response.message!!)
                }
            }
        }
    }
}
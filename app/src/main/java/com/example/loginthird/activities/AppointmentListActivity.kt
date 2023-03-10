package com.example.loginthird.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginthird.BaseActivity
import com.example.loginthird.R
import com.example.loginthird.Tools
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
    private lateinit var viewModel: AppointmentListViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AppointmentListViewmodel::class.java]
        binding = ActivityAppointmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()


        val sessionList = findViewById<RecyclerView>(R.id.session_list)
        sessionList.layoutManager = LinearLayoutManager(this)

        viewModel.sessions.observe(this) { sessions ->
            val adapter = SessionListAdapter(sessions)
            sessionList.adapter = adapter
        }

//        getSessionListFromAPI { sessions ->
//            val adapter = SessionListAdapter(sessions)
//            sessionList.adapter = adapter
//        }
    }

    private fun initToolbar() {
        val toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Session List"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appointments_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.refreshSessions()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.refreshSessionsIfNeeded()
    }

    private fun getSessionListFromAPI(function: (List<UISession>) -> Unit) {
        val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")
        val api = getApi()


        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = api.getSessions()
            withContext(Dispatchers.Main) {
                if (response.sessions != null) {
                    val apiSessionMapper = ApiSessionMapper()
                    val sessions = response.sessions.map { apiSessionMapper.mapToDomain(it) }
                    function(sessions)
                } else {
//                    toast(response.message!!)
                }
            }
        }
    }
}
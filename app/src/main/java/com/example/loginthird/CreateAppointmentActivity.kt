package com.example.loginthird

import android.os.Bundle
import com.example.loginthird.databinding.ActivityCreateAppointmentBinding
import com.example.loginthird.retrofit.RequestCreateSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAppointmentActivity : BaseActivity() {
    private lateinit var binding: ActivityCreateAppointmentBinding
    private lateinit var sessionDate: String
    private lateinit var service: String
    private lateinit var barber: String
    private lateinit var customerName: String
    private lateinit var contact: String
    private lateinit var dateOfBirth: String
    private lateinit var status: String
    private lateinit var location: String
    private lateinit var nextDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSaveNewAppointment.setOnClickListener {
            createSession {
                finish()
            }
        }
    }

    private fun createSession(function: () -> Unit) {
        val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")
        val api = getApi()

        sessionDate = binding.textviewSessionDate.text.toString()
        service = binding.edittextService.text.toString()
        barber = binding.edittextBarber.text.toString()
        customerName = binding.edittextCustomerName.text.toString()
        contact = binding.edittextCustomerContact.text.toString()
        nextDate = binding.textViewNextDate.text.toString()
        dateOfBirth = binding.textViewDateOfBirth.text.toString()
        location = binding.edittextLocation.text.toString()
        status = binding.edittextStatus.text.toString()

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = api.createSession(
                RequestCreateSession(
                    sessionDate,
                    service,
                    barber,
                    customerName,
                    contact,
                    nextDate,
                    dateOfBirth,
                    location,
                    status
                )
            )
            withContext(Dispatchers.Main) {
                if (response.session != null) {
                    toast("new session create and scheduled")
                    function()
                } else {
                    toast(response.message!!)
                }
            }
        }
    }
}
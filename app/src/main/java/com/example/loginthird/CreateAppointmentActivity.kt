package com.example.loginthird

import android.R
import android.app.DatePickerDialog
import android.content.pm.PackageInstaller.EXTRA_SESSION_ID
import android.os.Bundle
import android.text.Selection.setSelection
import android.view.View
import android.widget.*
import com.example.loginthird.cache.CachedSession
import com.example.loginthird.cache.SalonDatabase
import com.example.loginthird.cache.SessionDao
import com.example.loginthird.databinding.ActivityCreateAppointmentBinding
import com.example.loginthird.retrofit.RequestCreateSession
import com.example.loginthird.singleton.User
import kotlinx.coroutines.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

    private var sessionDao:SessionDao? = null
    private var isEditMode = false
    private var sessionId: String? = null
    private var statusSpinnerAdapter: ArrayAdapter<String>? = null
    private var originalSession: CachedSession? = null

    companion object {
        const val EXTRA_SESSION_ID = "extra_session_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionDao = SalonDatabase.getInstance(application).sessionDao()

        val items = listOf("pending", "done", "cancel")
        statusSpinnerAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, items)
        statusSpinnerAdapter!!.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        val spinner = binding.statusSpinner
        spinner.adapter = statusSpinnerAdapter
        val defaultPosition = 0
        spinner.setSelection(defaultPosition)

        if (intent.hasExtra(EXTRA_SESSION_ID)) {
            sessionId = intent.getStringExtra(EXTRA_SESSION_ID)
            isEditMode = false
            // Disable the edit texts and change the button text to "Edit"
            binding.textviewSessionDate.isEnabled = false
            binding.edittextService.isEnabled = false
            binding.edittextCustomerName.isEnabled = false
            binding.edittextCustomerContact.isEnabled = false
            binding.textViewDateOfBirth.isEnabled = false
            binding.edittextLocation.isEnabled = false
            binding.statusSpinner.isEnabled = false
            binding.textViewNextDate.isEnabled = false
            binding.buttonSaveNewAppointment.text = "Edit"
            // Load the item details into the edit texts
            loadItemDetails()
        } else {
            isEditMode = true
            val currentDate = Date()
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(currentDate)
            binding.textviewSessionDate.text = formattedDate
            binding.edittextBarber.setText(User.instance.userName)
        }




        val selectedItemPosition = spinner.selectedItemPosition
        status = if (selectedItemPosition == 0) {
            spinner.getItemAtPosition(0).toString()
        } else {
            spinner.selectedItem.toString()
        }


        binding.textviewSessionDate.setOnClickListener {
            showDatePickerDialog(binding.textviewSessionDate)
        }

        binding.textViewDateOfBirth.setOnClickListener {
            showDatePickerDialog(binding.textViewDateOfBirth)
        }

        binding.textViewNextDate.setOnClickListener {
            showDatePickerDialog(binding.textViewNextDate)
        }

        binding.buttonSaveNewAppointment.setOnClickListener {
            if (isEditMode) {
                createSession {
                    finish()
                }
            } else {
                updateSession {
                    finish()
                }
            }
        }
    }

    private fun updateSession(function: () -> Unit) {
        sessionDate = binding.textviewSessionDate.text.toString()
        service = binding.edittextService.text.toString()
        barber = binding.edittextBarber.text.toString()
        customerName = binding.edittextCustomerName.text.toString()
        contact = binding.edittextCustomerContact.text.toString()
        nextDate = binding.textViewNextDate.text.toString()
        dateOfBirth = binding.textViewDateOfBirth.text.toString()
        location = binding.edittextLocation.text.toString()

        val selectedItemPosition = binding.statusSpinner.selectedItemPosition
        status = if (selectedItemPosition == 0) {
            binding.statusSpinner.getItemAtPosition(0).toString()
        } else {
            binding.statusSpinner.selectedItem.toString()
        }

        sessionDao!!.updateSession(
            CachedSession(
                originalSession!!.sessionId,
                sessionDate,
                service,
                barber,
                customerName,
                contact,
                nextDate,
                dateOfBirth = dateOfBirth,
                location = location,
                status = status,
                createdAt = originalSession!!.createdAt,
                updatedAt = originalSession!!.updatedAt
            )
        )
    }

    private fun loadItemDetails() {
        runBlocking {
            val session = withContext(Dispatchers.IO) {
                sessionDao!!.getSessionById(sessionId!!)
            }
            originalSession = session
            // Load the item details into the edit texts
            binding.textviewSessionDate.text = session!!.sessionDate
            binding.edittextService.setText(session.service)
            binding.edittextCustomerName.setText(session.customerName)
            binding.edittextCustomerContact.setText(session.customerContact)
            binding.textViewDateOfBirth.text = session.dateOfBirth
            binding.edittextLocation.setText(session.location)

            val position = statusSpinnerAdapter?.getPosition(session.status)
            if (position != Spinner.INVALID_POSITION) {
                binding.statusSpinner.setSelection(position!!)
            }
            binding.textViewNextDate.text = session.nextAppointmentDate
        }
    }

    private fun checkDateFormats(dateList: List<String>): Boolean {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)

        for (dateText in dateList) {
            try {
                // Parse the input text using the date format
                dateFormat.parse(dateText)
            } catch (e: ParseException) {
                // Handle the exception if the input text is not in the expected format
                e.printStackTrace()
                return false
            }
        }
        return true

    }

    private fun createSession(function: () -> Unit) {
        val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")
        val api = getApi()

        sanitizeInput()
        getSpinnerSelection()


        sessionDate = binding.textviewSessionDate.text.toString()
        service = binding.edittextService.text.toString()
        barber = binding.edittextBarber.text.toString()
        customerName = binding.edittextCustomerName.text.toString()
        contact = binding.edittextCustomerContact.text.toString()
        nextDate = binding.textViewNextDate.text.toString()
        dateOfBirth = binding.textViewDateOfBirth.text.toString()
        location = binding.edittextLocation.text.toString()


        if (checkDateFormats(listOf(sessionDate, nextDate, dateOfBirth))) {

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

    private fun getSpinnerSelection() {
        binding.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedStatus = parent.getItemAtPosition(position) as String
                status = selectedStatus
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                status = "Pending"
            }
        }

    }

    private fun sanitizeInput() {
        val phoneNumberEditText = binding.edittextCustomerContact
        val phoneNumber = phoneNumberEditText.text.toString().trim()

        if (phoneNumber.length == 10 && phoneNumber.startsWith("0")) {
            val formattedPhoneNumber = "256" + phoneNumber.substring(1)
            phoneNumberEditText.setText(formattedPhoneNumber)
        } else if (phoneNumber.length == 10 && phoneNumber.startsWith("256")) {
            // Phone number is already formatted correctly, no need to do anything
        } else {
            toast("put valid contact please")
        }

    }

    private fun showDatePickerDialog(bt: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)

                bt.text = formattedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
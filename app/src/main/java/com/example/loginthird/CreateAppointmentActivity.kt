package com.example.loginthird

import android.app.DatePickerDialog
import android.content.pm.PackageInstaller.EXTRA_SESSION_ID
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.loginthird.api.models.CachedSessionToApiSession
import com.example.loginthird.cache.CachedSession
import com.example.loginthird.cache.SalonDatabase
import com.example.loginthird.cache.SessionDao
import com.example.loginthird.databinding.ActivityCreateSessionBinding
import com.example.loginthird.retrofit.RequestCreateSession
import com.example.loginthird.retrofit.RetrofitFactory
import com.example.loginthird.singleton.User
import kotlinx.coroutines.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CreateAppointmentActivity : BaseActivity() {
    private lateinit var binding: ActivityCreateSessionBinding
    private lateinit var sessionDate: String
    private lateinit var service: String
    private lateinit var barber: String
    private lateinit var customerName: String
    private lateinit var contact: String
    private lateinit var dateOfBirth: String
    private lateinit var status: String
    private lateinit var location: String
    private lateinit var nextDate: String

    private var sessionDao: SessionDao? = null
    private var isEditMode = false
    private var sessionId: String? = null
    private var originalSession: CachedSession? = null
    private var pageTitle:String = "Create Session"

    companion object {
        const val EXTRA_SESSION_ID = "extra_session_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionDao = SalonDatabase.getInstance(application).sessionDao()

        if (intent.hasExtra(EXTRA_SESSION_ID)) {
            sessionId = intent.getStringExtra(EXTRA_SESSION_ID)
            isEditMode = false
            pageTitle = "Edit Session"
            // Disable the edit texts and change the button text to "Edit"
            binding.pickerSessionDate.isEnabled = false
            binding.edittextService.isEnabled = false
            binding.edittextCustomer.isEnabled = false
            binding.edittextContact.isEnabled = false
            binding.pickerBirthDate.isEnabled = false
            binding.edittextLocation.isEnabled = false
//            binding.statusSpinner.isEnabled = false
//            binding.textViewNextDate.isEnabled = false
            binding.buttonSubmit.text = "Edit"
            // Load the item details into the edit texts
            loadItemDetails()
        } else {
            isEditMode = true
            val currentDate = Date()
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(currentDate)
            binding.pickerSessionDate.setText(formattedDate)
            binding.edittextBarber.setText(User.instance.userName)
        }

        initToolbar()

//        val selectedItemPosition = spinner.selectedItemPosition
//        status = if (selectedItemPosition == 0) {
//            spinner.getItemAtPosition(0).toString()
//        } else {
//            spinner.selectedItem.toString()
//        }


        binding.pickerSessionDate.setOnClickListener {
            showDatePickerDialog(binding.pickerSessionDate)
        }

        binding.pickerBirthDate.setOnClickListener {
            showDatePickerDialog(binding.pickerBirthDate)
        }

        binding.pickerNextDate.setOnClickListener {
            showDatePickerDialog(binding.pickerNextDate)
        }

        binding.buttonSubmit.setOnClickListener {
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

        binding.pickerStatus.setOnClickListener {
            showStatusDialog(binding.pickerStatus)
        }
    }

    private fun showStatusDialog(v: View) {
        val array = arrayOf(
            "pending", "done", "cancel"
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Status")
        builder.setSingleChoiceItems(
            array, -1
        ) { dialogInterface, i ->
            (v as EditText).setText(array[i])
            dialogInterface.dismiss()
        }
        builder.show()
    }

    private fun initToolbar() {
        val toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = pageTitle
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
    }

    private fun updateSession(function: () -> Unit) {
        sessionDate = binding.pickerSessionDate.text.toString()
        service = binding.edittextService.text.toString()
        barber = binding.edittextBarber.text.toString()
        customerName = binding.edittextCustomer.text.toString()
        contact = binding.edittextContact.text.toString()
        nextDate = binding.pickerNextDate.text.toString()
        dateOfBirth = binding.pickerBirthDate.text.toString()
        location = binding.edittextLocation.text.toString()

//        val selectedItemPosition = binding.statusSpinner.selectedItemPosition
//        status = if (selectedItemPosition == 0) {
//            binding.statusSpinner.getItemAtPosition(0).toString()
//        } else {
//            binding.statusSpinner.selectedItem.toString()
//        }

        val newSession = CachedSession(
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

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                sessionDao!!.updateSession(
                    newSession
                )
                val apiSessionVersion = CachedSessionToApiSession(newSession)
                try {
                    RetrofitFactory.createConnectionService().editSession(
                        apiSessionVersion.sessionId!!,
                        apiSessionVersion
                    )
                } catch (e: Exception) {
//                    toast("not api updated coz ${e.message}")
                    // Error
                }
            }
            function()
        }

    }

    private fun loadItemDetails() {
        runBlocking {
            val session = withContext(Dispatchers.IO) {
                sessionDao!!.getSessionById(sessionId!!)
            }
            originalSession = session
            // Load the item details into the edit texts
            binding.pickerSessionDate.setText(session!!.sessionDate)
            binding.edittextService.setText(session.service)
            binding.edittextBarber.setText(session.barber)
            binding.edittextCustomer.setText(session.customerName)
            binding.edittextContact.setText(session.customerContact)
            binding.pickerBirthDate.setText(session.dateOfBirth)
            binding.edittextLocation.setText(session.location)

//            val position = statusSpinnerAdapter?.getPosition(session.status)
//            if (position != Spinner.INVALID_POSITION) {
//                binding.statusSpinner.setSelection(position!!)
//            }
            binding.pickerNextDate.setText(session.nextAppointmentDate)
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


        sessionDate = binding.pickerSessionDate.text.toString()
        service = binding.edittextService.text.toString()
        barber = binding.edittextBarber.text.toString()
        customerName = binding.edittextCustomer.text.toString()
        contact = binding.edittextContact.text.toString()
        nextDate = binding.pickerNextDate.text.toString()
        dateOfBirth = binding.pickerBirthDate.text.toString()
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
//        binding.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                val selectedStatus = parent.getItemAtPosition(position) as String
//                status = selectedStatus
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                status = "Pending"
//            }
//        }

    }

    private fun sanitizeInput() {
        val phoneNumberEditText = binding.edittextContact
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
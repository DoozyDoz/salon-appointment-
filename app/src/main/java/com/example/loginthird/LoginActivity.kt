package com.example.loginthird

import android.os.Bundle
import com.example.loginthird.databinding.ActivityLoginBinding
import com.example.loginthird.retrofit.ApiLoggedInUser
import com.example.loginthird.retrofit.RequestLogin
import com.example.loginthird.singleton.User
import kotlinx.coroutines.*

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            signinUser {
                openActivity(DashboardActivity::class.java)
            }
        }
    }

    private fun signinUser(function: () -> Unit) {
        val exceptionHandler = createExceptionHandler(message = "Failed to search remotely.")
        val api = getApi()

        email = binding.textInputEmail.text.toString()
        password = binding.textInputPassword.text.toString()

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = api.login(RequestLogin(email, password))
            withContext(Dispatchers.Main) {
                if (response.loggedInUser != null) {
                    saveUser(response.loggedInUser)
                    function()
                } else {
//                    toast(response.message!!)
                }
            }
        }
    }

    private fun saveUser(user: ApiLoggedInUser) {
        User.instance.userId = user.userId
        User.instance.userName = user.name
        User.instance.userRole = user.role
    }




}
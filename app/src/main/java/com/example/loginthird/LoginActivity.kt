package com.example.loginthird

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginthird.api.interceptor.TokenInterceptor
import com.example.loginthird.databinding.ActivityLoginBinding
import com.example.loginthird.retrofit.ApiLoggedInUser
import com.example.loginthird.retrofit.ConnectionService
import com.example.loginthird.retrofit.ReqLogin
import com.example.loginthird.retrofit.ResponseLogin
import com.example.loginthird.singleton.User
import kotlinx.coroutines.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class LoginActivity : AppCompatActivity() {

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
            val response = api.login(ReqLogin(email, password))
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

    private fun createExceptionHandler(message: String): CoroutineExceptionHandler {
        return GlobalScope.createExceptionHandler(message) {
            toast(it.message!!)
        }
    }

    private fun getApi(): ConnectionService {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(getOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ConnectionService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(this))
            .build()
    }
}
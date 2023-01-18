package com.example.loginthird

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginthird.databinding.ActivityLoginBinding
import com.example.loginthird.retrofit.ConnectionService
import com.example.loginthird.retrofit.ResponseLogin
import okhttp3.Callback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            login {
                openActivity(DashboardActivity::class.java)
            }
        }
    }

    private fun login(function: () -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.44:5000/api/v1/")
            .build()

        val service = retrofit.create(ConnectionService::class.java)
        val call =
            service.login(binding.textInputEmail.toString(), binding.textInputPassword.toString())

        call.enqueue(object : retrofit2.Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    val cookies = response.headers().values("Set-Cookie")
                    for (cookie in cookies) {
                        if (cookie.startsWith("token=")) {
                            val token = cookie.substringAfter("token=")
                            val editor = getSharedPreferences("prefs", MODE_PRIVATE).edit()
                            editor.putString("token", token)
                            editor.apply()
                        }
                    }
                    function
                } else {
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
//                TODO("Not yet implemented")
            }
        })
    }
}
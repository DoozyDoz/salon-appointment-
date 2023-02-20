package com.example.loginthird

import android.os.Bundle
import com.example.loginthird.databinding.ActivityCreateSessionBinding
import com.example.loginthird.databinding.ActivityLoginBinding
import com.example.loginthird.retrofit.ApiLoggedInUser
import com.example.loginthird.retrofit.RequestLogin
import com.example.loginthird.singleton.User
import kotlinx.coroutines.*

class TestActivity : BaseActivity() {

    private lateinit var binding: ActivityCreateSessionBinding
    private lateinit var email: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }






}
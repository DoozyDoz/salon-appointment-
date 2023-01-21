package com.example.loginthird.singleton

class User private constructor() {
    companion object {
        val instance: User by lazy { User() }
    }

    var userId: String? = null
    var userName: String? = null
    var userRole: String? = "user"
}

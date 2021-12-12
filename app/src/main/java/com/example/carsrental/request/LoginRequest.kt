package com.example.carsrental.request

import com.google.gson.annotations.Expose

data class LoginRequest(
    @Expose
    val email: String?,
    @Expose
    val password: String?
)
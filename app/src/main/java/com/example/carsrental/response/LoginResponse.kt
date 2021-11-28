package com.example.carsrental.response

data class LoginResponse(
    val userId: String?, val token: String?, val error: String?
)
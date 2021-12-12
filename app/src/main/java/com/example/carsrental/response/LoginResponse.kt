package com.example.carsrental.response

import com.google.gson.annotations.Expose

data class LoginResponse(
    @Expose val id: String?, @Expose val token: String?
)
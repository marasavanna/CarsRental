package com.example.carsrental.repository

import com.example.carsrental.api.AuthService
import com.example.carsrental.request.LoginRequest

class AuthRepository(val api: AuthService) {

    fun login(loginRequest: LoginRequest) = api.login(loginRequest)
}
package com.example.carsrental.api

import androidx.lifecycle.LiveData
import com.example.carsrental.request.LoginRequest
import com.example.carsrental.response.LoginResponse
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("authenticate/login")
    fun login(@Body loginRequest: LoginRequest): Observable<LoginResponse>
}
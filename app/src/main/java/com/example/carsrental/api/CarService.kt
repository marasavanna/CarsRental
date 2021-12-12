package com.example.carsrental.api


import com.example.carsrental.request.CarInputRequest
import com.example.carsrental.response.CarResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface CarService {

    @GET("/api/cars")
    fun getCars(): Observable<List<CarResponse>>


    @PUT("/api/cars")
    fun updateCar(@Body carInputRequest: CarInputRequest): Observable<ResponseBody>
}
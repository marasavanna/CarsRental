package com.example.carsrental.mapper

import android.content.Context
import com.example.carsrental.model.Car
import com.example.carsrental.request.CarInputRequest
import com.example.carsrental.response.CarResponse
import com.example.carsrental.utils.PreferenceHelper

class CarMapper(val context: Context) {

    private fun mapCarResponseToCarModel(carResponse: CarResponse): Car {
        carResponse.apply {
            return Car(id, brand, model, fabricationYear, color, image, isAutomatic)
        }
    }

    fun mapResponseToCars(carResponses: List<CarResponse>): List<Car> {
        return carResponses.map { mapCarResponseToCarModel(it) }
    }

    fun mapCarToInputRequest(car: Car): CarInputRequest {
        car.apply {
            return CarInputRequest(
                brand,
                model,
                fabricationYear,
                color,
                image,
                isAutomatic,
                PreferenceHelper.customPrefs(context, PreferenceHelper.prefsName)
                    .getString(PreferenceHelper.userId, ""),
                id
            )
        }
    }
}
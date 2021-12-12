package com.example.carsrental.viewmodel

import com.example.carsrental.mapper.CarMapper
import com.example.carsrental.repository.CarRepository

class AddCarViewModel(val repository: CarRepository, val mapper: CarMapper): CarInputViewModel() {


    override fun onSubmit() {

    }

    fun addNewCar() {

    }
}
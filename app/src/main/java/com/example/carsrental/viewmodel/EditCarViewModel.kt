package com.example.carsrental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsrental.model.Car

class EditCarViewModel: ViewModel() {

    private var car: Car? = null

    private val _brand = MutableLiveData<String>()
    private val _model = MutableLiveData<String>()
    private val _year = MutableLiveData<String>()
    private val _isAutomatic = MutableLiveData(false)

    var brand : LiveData<String> = _brand
    var model : LiveData<String> = _model
    var year : LiveData<String> = _year
    var isAutomatic : MutableLiveData<Boolean> = _isAutomatic

    fun setCarObject(car: Car) {
        this.car = car
        _brand.value = car.brand
        _model.value = car.model
        _year.value = car.fabricationYear?.toString()
        _isAutomatic.value = car.isAutomatic ?: false
    }

    fun onBrandChange(newBrand: String) {
        _brand.value = newBrand
    }
    fun onModelChange(model: String) {
        _model.value = model
    }
    fun onYearChange(year: String) {
        _year.value = year
    }
    fun onIsAutomaticChange(isAutomatic: Boolean) {
        _isAutomatic.value = isAutomatic
    }
}
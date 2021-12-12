package com.example.carsrental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsrental.mapper.CarMapper
import com.example.carsrental.model.Car
import com.example.carsrental.repository.CarRepository
import com.example.carsrental.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception

class CarsListViewModel(val carRepository: CarRepository) : ViewModel() {

    private val _cars = MutableLiveData<MutableList<Car>>()
    var cars: LiveData<MutableList<Car>> = _cars

    private val _searchKey = MutableLiveData("")
    var searchKey: LiveData<String> = _searchKey

    private val _exception = MutableLiveData<String>()
    var exception: LiveData<String> = _exception

    var carToEdit = SingleLiveEvent<Car>()


    fun onCarsSearched(searchKey: String) {
        _searchKey.value = searchKey
    }

    fun onCarSwipedToDelete(car: Car) {
        _cars.value = cars.value?.filter { it != car }?.toMutableList()
    }

    fun onEditCar(car: Car) {
        carToEdit.value = car
    }

    fun fetchCarsData() {
        carRepository.getSyncedCars(_cars, _exception)
    }
}
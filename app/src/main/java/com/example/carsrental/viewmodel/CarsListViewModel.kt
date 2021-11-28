package com.example.carsrental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsrental.model.Car
import com.example.carsrental.utils.SingleLiveEvent

class CarsListViewModel : ViewModel() {

    private val _cars = MutableLiveData<MutableList<Car>>()
    var cars: LiveData<MutableList<Car>> = _cars

    private val _searchKey = MutableLiveData("")
    var searchKey: LiveData<String> = _searchKey

    var carToEdit = SingleLiveEvent<Car>()


    init {
        fetchCarsData()
    }

    fun onCarsSearched(searchKey: String) {
        _searchKey.value = searchKey
    }


    fun onCarSwipedToDelete(car: Car) {
        _cars.value = cars.value?.filter { it != car }?.toMutableList()
    }

    fun onEditCar(car: Car) {
        carToEdit.value = car
    }

    private fun fetchCarsData() {
        val cars = mutableListOf<Car>()
        for (i in 0..8) {
            cars.add(
                Car(
                    i, "Lamorghini",
                    "Galardo", 2020, "blue",
                    "https://car-pictures-download.com/wp-content/uploads/McLaren-570S-Coupe-wallpaper-4K-Ultra-HD.jpg",
                    true, "88"
                )
            )
        }
        _cars.value = cars
    }
}
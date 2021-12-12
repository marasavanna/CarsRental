package com.example.carsrental.repository

import androidx.lifecycle.MutableLiveData
import com.example.carsrental.api.CarService
import com.example.carsrental.mapper.CarMapper
import com.example.carsrental.model.Car
import com.example.carsrental.persistance.CarDatabase
import com.example.carsrental.request.CarInputRequest
import com.example.carsrental.utils.CarNetworkManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class CarRepository(
    val carService: CarService,
    val database: CarDatabase,
    val carMapper: CarMapper,
    val carNetworkManager: CarNetworkManager
) {

    fun getSyncedCars(
        carsList: MutableLiveData<MutableList<Car>>,
        exception: MutableLiveData<String>
    ) {
        if (carNetworkManager.isNetworkAvailable()) {
            getCars(carsList, exception)
        } else {
            getCarsFromLocal(carsList, exception)
        }
    }

    fun getCars(carsList: MutableLiveData<MutableList<Car>>, exception: MutableLiveData<String>) {
        carService.getCars().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val cars = carMapper.mapResponseToCars(it).toMutableList()
                insertCarsLocally(cars, carsList, exception)
            }, {
                exception.value = it.message
            })
    }

    private fun insertCarsLocally(
        cars: MutableList<Car>,
        carsList: MutableLiveData<MutableList<Car>>,
        exception: MutableLiveData<String>
    ) {
        database.carDao().insertAll(cars).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                getCarsFromLocal(carsList, exception)
            }, {
                exception.value = it.message
            })
    }

    private fun getCarsFromLocal(
        carsList: MutableLiveData<MutableList<Car>>,
        exception: MutableLiveData<String>
    ) {
        database.carDao().getCars().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                carsList.value = it
            }, {
                exception.value = it.message
            })
    }


    fun editCar(editCarResponse: CarInputRequest) = carService.updateCar(editCarResponse)

}
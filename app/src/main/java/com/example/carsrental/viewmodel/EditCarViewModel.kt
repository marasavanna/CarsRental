package com.example.carsrental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carsrental.mapper.CarMapper
import com.example.carsrental.model.Car
import com.example.carsrental.repository.CarRepository
import com.example.carsrental.utils.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class EditCarViewModel(val carRepository: CarRepository, val mapper: CarMapper) :
    CarInputViewModel() {

    private var _car: MutableLiveData<Car> = MutableLiveData<Car>()
    val car: LiveData<Car> = _car
    var carEdited = SingleLiveEvent<Boolean>()

    fun setCarObject(car: Car) {
        _brand.value = car.brand
        _model.value = car.model
        _year.value = car.fabricationYear?.toString()
        _isAutomatic.value = car.isAutomatic ?: false
        _carImage.value = car.image
    }

    fun getCarById(id: Int) {
        carRepository.getCarById(id, _car)
    }

    private fun editCar() {
        val car = Car(
            _car.value?.id,
            _brand.value,
            model.value,
            _car.value?.fabricationYear,
            _car.value?.color,
            _carImage.value,
            isAutomatic.value
        )

        carRepository.editCar(
            mapper.mapCarToInputRequest(
                car
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                carEdited.value = true
            }, {

            })

    }

    override fun onSubmit() {
        editCar()
    }

}
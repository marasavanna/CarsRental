package com.example.carsrental.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsrental.utils.ImageUtils.toBase64Image

abstract class CarInputViewModel: ViewModel() {

    protected val _brand = MutableLiveData<String>()
    protected val _model = MutableLiveData<String>()
    protected val _year = MutableLiveData<String>()
    protected val _isAutomatic = MutableLiveData(false)
    protected val _carImage = MutableLiveData<String>()

    var brand : LiveData<String> = _brand
    var model : LiveData<String> = _model
    var year : LiveData<String> = _year
    var isAutomatic : MutableLiveData<Boolean> = _isAutomatic
    var carImage : LiveData<String> = _carImage

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

    private fun onImageChange(newImage: String) {
        _carImage.value = newImage
    }

    abstract fun onSubmit()

    fun onCameraTriggered() {

    }

    fun onGalleryTriggered(uri: Uri?, context: Context) {
        onImageChange(uri.toBase64Image(context))
    }

}
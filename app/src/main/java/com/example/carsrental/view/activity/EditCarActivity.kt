package com.example.carsrental.view.activity

import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.carsrental.model.Car
import com.example.carsrental.view.activity.CarsListActivity.Companion.car_key
import com.example.carsrental.view.composable.CarForm
import com.example.carsrental.view.composable.CarInputScreen
import com.example.carsrental.viewmodel.CarsListViewModel
import com.example.carsrental.viewmodel.EditCarViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCarActivity : AppCompatActivity() {

    val viewModel: EditCarViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.carEdited.observe(this) {
            if (it) {
                finish()
                viewModel.carEdited.value = false
            }
        }

        intent?.getParcelableExtra<Car>(car_key)?.let { car ->
            viewModel.setCarObject(car)
            setContent {
                CarInputScreen(viewModel = viewModel, backAction = ::finish, "Edit " + car.model)
            }
        }
    }
}
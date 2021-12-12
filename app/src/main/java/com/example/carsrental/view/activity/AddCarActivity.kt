package com.example.carsrental.view.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.carsrental.view.composable.CarInputScreen
import com.example.carsrental.viewmodel.AddCarViewModel
import com.example.carsrental.viewmodel.EditCarViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCarActivity : AppCompatActivity() {

    val viewModel: AddCarViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CarInputScreen(viewModel = viewModel, backAction = { finish()}, title = "Add new car")
        }
    }
}
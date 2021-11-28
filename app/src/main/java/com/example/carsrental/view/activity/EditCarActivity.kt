package com.example.carsrental.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import com.example.carsrental.model.Car
import com.example.carsrental.view.activity.CarsListActivity.Companion.car_key
import com.example.carsrental.viewmodel.CarsListViewModel
import com.example.carsrental.viewmodel.EditCarViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditCarActivity : AppCompatActivity() {

    val viewModel: EditCarViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.getParcelableExtra<Car>(car_key)?.let { car ->
            viewModel.setCarObject(car)
            setContent {
                CarForm(viewModel = viewModel)
            }
        }
    }


    @Composable fun CarForm(viewModel: EditCarViewModel) {
        val brand by viewModel.brand.observeAsState(viewModel.brand.value)
        val model by viewModel.model.observeAsState(viewModel.model.value)
        val fabricationYear by viewModel.year.observeAsState(viewModel.year.value)
        val isAutomatic by viewModel.isAutomatic.observeAsState( false)

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            CarFieldInput(label = "Brand*", text = brand ?: "", onTextChange = viewModel::onBrandChange)
            CarFieldInput(label = "Model*", text = model ?: "", onTextChange = viewModel::onModelChange)
            CarFieldInput(label = "Fabrication Year*", text = fabricationYear ?: "", onTextChange = viewModel::onYearChange)
            CarCheckbox(isChecked = isAutomatic, onCheckChange = viewModel::onIsAutomaticChange)
        }
    }

    @Composable
    fun CarFieldInput(label: String, text: String, onTextChange: (text: String) -> Unit) {
        Row() {
            OutlinedTextField(value = text, onValueChange = {
                onTextChange(it)
            }, label = { Text(text = label) }
            )
        }
    }

    @Composable fun CarCheckbox(isChecked: Boolean, onCheckChange: (checkStatus: Boolean) -> Unit) {
        Checkbox(checked = isChecked , onCheckedChange = {
            onCheckChange(it)
        })
    }
}
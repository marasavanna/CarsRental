package com.example.carsrental.di

import com.example.carsrental.viewmodel.AuthenticationViewModel
import com.example.carsrental.viewmodel.CarsListViewModel
import com.example.carsrental.viewmodel.EditCarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { AuthenticationViewModel() }
    viewModel { CarsListViewModel() }
    viewModel { EditCarViewModel() }
}
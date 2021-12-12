package com.example.carsrental.di

import android.content.Context
import androidx.room.Room
import com.example.carsrental.api.AuthService
import com.example.carsrental.api.CarService
import com.example.carsrental.mapper.CarMapper
import com.example.carsrental.persistance.CarDatabase
import com.example.carsrental.repository.AuthRepository
import com.example.carsrental.repository.CarRepository
import com.example.carsrental.utils.CarNetworkManager
import com.example.carsrental.viewmodel.AddCarViewModel
import com.example.carsrental.viewmodel.AuthenticationViewModel
import com.example.carsrental.viewmodel.CarsListViewModel
import com.example.carsrental.viewmodel.EditCarViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


fun  provideAuthRepository(apiService: AuthService): AuthRepository =
    AuthRepository(apiService)

fun provideCarRepository(apiService: CarService, carDatabase: CarDatabase, carMapper: CarMapper, carNetworkManager: CarNetworkManager): CarRepository =
    CarRepository(apiService,carDatabase, carMapper, carNetworkManager)

fun provideCarMapper(context: Context): CarMapper = CarMapper(context)


fun provideCarDatabase(context: Context) : CarDatabase {
    return Room.databaseBuilder(context, CarDatabase::class.java, "name").build()
}

fun provideNetworkManager(context: Context) = CarNetworkManager(context)

val viewModelModule = module {

    single {
        provideCarRepository(get(), get(), get(), get())
    }

    single {
        provideAuthRepository(get())
    }

    single {
        provideCarMapper(androidContext())
    }
    single {
        provideNetworkManager(androidContext())
    }
    single { provideCarDatabase(androidContext()) }

    viewModel { AuthenticationViewModel(get()) }
    viewModel { CarsListViewModel(get()) }
    viewModel { EditCarViewModel(get(), get()) }
    viewModel { AddCarViewModel(get(), get()) }
}


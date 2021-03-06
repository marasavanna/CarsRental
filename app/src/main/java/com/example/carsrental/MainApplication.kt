package com.example.carsrental

import android.app.Application
import com.example.carsrental.di.networkModule
import com.example.carsrental.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(viewModelModule, networkModule))
        }
    }
}
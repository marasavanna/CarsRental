package com.example.carsrental.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.carsrental.model.Car

@Database(entities = [Car::class], version = 1)
abstract class CarDatabase: RoomDatabase() {
    abstract fun carDao(): CarDao
}
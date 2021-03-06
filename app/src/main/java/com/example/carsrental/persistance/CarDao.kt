package com.example.carsrental.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carsrental.model.Car
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface CarDao {

    @Query("SELECT * from car")
    fun getCars(): Maybe<MutableList<Car>>

    @Query("SELECT * from car WHERE id = :carId")
    fun getCarById(carId: Int): Maybe<Car>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cars: MutableList<Car>): Completable

    @Query("DELETE FROM car")
    fun clearAllCars(): Completable
}
package com.example.carsrental.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Car(
    @PrimaryKey(autoGenerate = false)
    var id: Int?,
    val brand: String?,
    val model: String?,
    val fabricationYear: Int?,
    val color: String?,
    var image: String?,
    var isAutomatic: Boolean?
): Parcelable
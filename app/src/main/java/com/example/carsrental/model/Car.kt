package com.example.carsrental.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Car(
    var id: Int?,
    val brand: String?,
    val model: String?,
    val fabricationYear: Int?,
    val color: String?,
    val image: String?,
    val isAutomatic: Boolean?,
    val userId: String?
): Parcelable
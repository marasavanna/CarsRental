package com.example.carsrental.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


class CarResponse (
    @Expose var id: Int?,
    @Expose val brand: String?,
    @Expose val model: String?,
    @Expose val fabricationYear: Int?,
    @Expose val color: String?,
    @Expose val image: String?,
    @Expose val isAutomatic: Boolean?,
    @Expose val userId: String?
)


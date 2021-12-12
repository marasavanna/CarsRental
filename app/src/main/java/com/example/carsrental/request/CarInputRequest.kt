package com.example.carsrental.request

import com.google.gson.annotations.Expose

data class CarInputRequest(
    @Expose val brand: String?,
    @Expose val model: String?,
    @Expose val fabricationYear: Int?,
    @Expose val color: String?,
    @Expose val image: String?,
    @Expose val isAutomatic: Boolean?,
    @Expose val userId: String?,
    @Expose val id: Int? = 0
)
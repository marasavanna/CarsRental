package com.example.carsrental.utils

object Extensions {

    fun String.isValidEmail() = contains("@") && contains(".com")
    fun String.isValidPassword() = length >= 6
}
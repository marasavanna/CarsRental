package com.example.carsrental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsrental.utils.Extensions.isValidEmail
import com.example.carsrental.utils.Extensions.isValidPassword
import com.example.carsrental.response.LoginResponse

class AuthenticationViewModel : ViewModel() {
    private val _textEmail = MutableLiveData<String>()
    var textEmail: MutableLiveData<String> = _textEmail
    private val _isEmailValid = MutableLiveData<Boolean>()
    var isEmailValid: MutableLiveData<Boolean> = _isEmailValid
    private val _emailLabel = MutableLiveData<String>()
    var emailLabel: MutableLiveData<String> = _emailLabel

    private val _textPassword = MutableLiveData<String>()
    var textPassword: MutableLiveData<String> = _textPassword
    private val _isPasswordValid = MutableLiveData<Boolean>()
    var isPasswordValid: MutableLiveData<Boolean> = _isPasswordValid
    private val _passwordLabel = MutableLiveData<String>()
    var passwordLabel: MutableLiveData<String> = _passwordLabel

    private val _loginResponse = MutableLiveData<LoginResponse>()
    var loginResponse: LiveData<LoginResponse> = _loginResponse

    fun onEmailChange(newText: String) {
        _textEmail.value = newText
        if (isEmailValid.value != null && isEmailValid.value == false) {
            validateEmail()
        }
    }

    fun onPasswordChange(newText: String) {
        _textPassword.value = newText
        validatePassword()
    }

    fun validateEmail() {
        isEmailValid.value = (textEmail.value ?: "").isValidEmail()
        emailLabel.value = if (isEmailValid.value != null && isEmailValid.value != true) {
            "Please input a valid email"
        } else {
            "Email"
        }
    }

    fun validatePassword() {
        isPasswordValid.value = _textPassword.value?.isValidPassword()
    }


    fun login() {
        
        _loginResponse.value = LoginResponse("99", "betoken", "")
    }
}
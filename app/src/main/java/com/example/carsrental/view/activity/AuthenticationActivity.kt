package com.example.carsrental.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent

import com.example.carsrental.viewmodel.AuthenticationViewModel
import com.example.carsrental.utils.PreferenceHelper

import com.example.carsrental.utils.PreferenceHelper.get
import com.example.carsrental.utils.PreferenceHelper.set
import com.example.carsrental.view.composable.RegistrationScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationActivity : AppCompatActivity() {

    val viewModel: AuthenticationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleLoggedInUserNavigation()
        setContent {
            RegistrationScreen(viewModel)
        }
        viewModel.loginResponse.observe(this) {
            PreferenceHelper.customPrefs(this, PreferenceHelper.prefsName).apply {
                this[PreferenceHelper.token] = it.token
                this[PreferenceHelper.userId] = it.id
            }
            navigateToCarsListScreen()
        }
    }


    private fun navigateToCarsListScreen() {
        startActivity(Intent(this, CarsListActivity::class.java))
    }

    private fun handleLoggedInUserNavigation() {
        if (PreferenceHelper.customPrefs(
                this,
                PreferenceHelper.prefsName
            )[PreferenceHelper.token, ""].isNotBlank()
        ) {
            navigateToCarsListScreen()
            finish()
        }
    }
}
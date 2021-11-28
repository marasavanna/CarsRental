package com.example.carsrental.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carsrental.viewmodel.AuthenticationViewModel
import com.example.carsrental.utils.PreferenceHelper
import com.example.carsrental.R
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
            PreferenceHelper.customPrefs(this, PreferenceHelper.prefsName)
            navigateToCarsListScreen()
        }
    }

    private fun navigateToCarsListScreen() {
        startActivity(Intent(this, CarsListActivity::class.java))
    }

    private fun handleLoggedInUserNavigation() {
        if (PreferenceHelper.token.isNotBlank()) {
            navigateToCarsListScreen()
            finish()
        }
    }
}
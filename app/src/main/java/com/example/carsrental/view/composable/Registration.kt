package com.example.carsrental.view.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.carsrental.R
import com.example.carsrental.viewmodel.AuthenticationViewModel


@Composable
fun RegistrationInputBox(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    validate: () -> Unit,
    isValid: Boolean,
    modifier: Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = { newText ->
            onTextChange(newText)
        },
        label = { Text(text = label) },
        singleLine = true,
        isError = !isValid,
        keyboardActions = KeyboardActions {
            validate()
        },
        textStyle = TextStyle(Color.Black),
        trailingIcon = {
            if (!isValid) {
                Icon(Icons.Filled.AccountBox, "error", tint = MaterialTheme.colors.error)
            }
        }
    )
}

@Composable
fun RegistrationForm(authenticationViewModel: AuthenticationViewModel, formModifier: Modifier) {
    val emailText by authenticationViewModel.textEmail.observeAsState("")
    val emailLabel by authenticationViewModel.emailLabel.observeAsState("Email")
    val isEmailValid by authenticationViewModel.isEmailValid.observeAsState(true)

    val passwordText by authenticationViewModel.textPassword.observeAsState("")
    val passwordLabel by authenticationViewModel.passwordLabel.observeAsState("Password")

    val passwordInputFocusedModifier = Modifier.onFocusChanged {
        if (it.isFocused) {
            authenticationViewModel.validateEmail()
        }
    }


    Column(
        modifier = formModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)) {
            RegistrationInputBox(
                text = emailText,
                onTextChange = authenticationViewModel::onEmailChange,
                label = emailLabel,
                validate = { authenticationViewModel.validateEmail() },
                isValid = isEmailValid,
                modifier = Modifier.clipToBounds()
            )
        }
        Row {
            RegistrationInputBox(
                text = passwordText,
                onTextChange = authenticationViewModel::onPasswordChange,
                label = passwordLabel,
                validate = { authenticationViewModel.validatePassword() },
                isValid = true,
                modifier = passwordInputFocusedModifier
            )
        }
    }
}

@Composable
fun LoginButton(authenticationViewModel: AuthenticationViewModel, modifier: Modifier) {
    val isEmailValid by authenticationViewModel.isEmailValid.observeAsState(false)
    val isPasswordValid by authenticationViewModel.isPasswordValid.observeAsState(false)
    Button(
        modifier = modifier,
        onClick = { authenticationViewModel.login() },
        enabled = isEmailValid && isPasswordValid
    ) {
        Text(text = "Log In".uppercase())
    }
}

@Composable
fun AppTitle() {
    Row(Modifier.padding(0.dp, 56.dp, 0.dp, 32.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_car_rental_24),
            contentDescription = null,
            Modifier.size(48.dp)
        )
        Text(
            modifier = Modifier.padding(12.dp, 12.dp, 0.dp, 0.dp),
            text = "Car Rentals",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}

@Composable
fun RegistrationScreen(authenticationViewModel: AuthenticationViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val formModifier = Modifier.padding(0.dp, 48.dp)
        val buttonModifier = Modifier
            .padding(0.dp, 48.dp, 0.dp, 0.dp)
            .fillMaxWidth(0.4f)
        AppTitle()
        Text(
            text = "Log in",
            Modifier.padding(0.dp, 32.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp
        )
        RegistrationForm(authenticationViewModel = authenticationViewModel, formModifier)
        LoginButton(authenticationViewModel = authenticationViewModel, buttonModifier)
    }
}
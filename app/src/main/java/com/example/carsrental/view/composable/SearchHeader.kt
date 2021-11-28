package com.example.carsrental.view.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carsrental.R
import com.example.carsrental.viewmodel.CarsListViewModel

@Composable
fun CarSearch(carsListViewModel: CarsListViewModel) {
    val searchKey by carsListViewModel.searchKey.observeAsState("")
    OutlinedTextField(
        value = searchKey,
        onValueChange = { newText ->
            carsListViewModel.onCarsSearched(newText)
        },
        label = { Text(text = "Search Car", color = Color.White)  },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        shape = RoundedCornerShape(12.dp),
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                "Search",
                tint = Color.White
            )
        },
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 16.sp
        ),
        trailingIcon = {
            Icon(
                Icons.Filled.Info,
                "Filter",
                tint = Color.White
            )
        })
}

@Composable
fun CarListHeader(modifier: Modifier, carsListViewModel: CarsListViewModel) {
    Column(
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.End, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                contentDescription = null,
                Modifier.size(24.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(16.dp, 2.dp),
                text = "Cars for Rent",
                color = Color.White,
                fontSize = 20.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CarSearch(carsListViewModel =  carsListViewModel)
        }
    }
}
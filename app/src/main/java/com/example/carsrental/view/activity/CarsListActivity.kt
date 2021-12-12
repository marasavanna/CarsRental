package com.example.carsrental.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.carsrental.view.composable.CarItemsList
import com.example.carsrental.viewmodel.CarsListViewModel
import com.example.carsrental.view.composable.CarListHeader
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.viewmodel.ext.android.viewModel


class CarsListActivity : AppCompatActivity() {

    val viewModel: CarsListViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CollapsingToolbarScaffold(
                modifier = Modifier.fillMaxSize(),
                state = rememberCollapsingToolbarScaffoldState(),
                scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
                toolbar = {
                    val modifier = Modifier
                        .road(
                            whenCollapsed = Alignment.CenterStart,
                            whenExpanded = Alignment.BottomEnd
                        )
                        .height(180.dp)
                        .fillMaxWidth()
                        .background(Color.Gray)
                    CarListHeader(modifier = modifier, viewModel)
                },
            ) {
                Scaffold(
                    isFloatingActionButtonDocked = true,
                    floatingActionButton = {
                        FloatingActionButton(onClick = { navigateToAddCar() }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                        }
                    },
                    content = {
                        Column(modifier = Modifier.padding(bottom = 32.dp)) {
                            CarItemsList(viewModel)
                        }
                    },
                )

            }
        }

        viewModel.carToEdit.observe(this) {
            val intent = Intent(this, EditCarActivity::class.java)
            intent.putExtra(car_key, it.apply {})
            startActivity(intent)
        }
    }

    private fun navigateToAddCar() {
        startActivity(Intent(this, AddCarActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchCarsData()
    }

    companion object {
        const val car_key = "car"
    }
}
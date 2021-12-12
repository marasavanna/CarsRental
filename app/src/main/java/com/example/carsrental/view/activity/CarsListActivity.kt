package com.example.carsrental.view.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
import com.example.carsrental.utils.InternetService
import com.example.carsrental.utils.InternetService.Companion.onlineStatusKey
import com.example.carsrental.view.composable.CarItemsList
import com.example.carsrental.viewmodel.CarsListViewModel
import com.example.carsrental.view.composable.CarListHeader
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.viewmodel.ext.android.viewModel


class CarsListActivity : AppCompatActivity() {

    val viewModel: CarsListViewModel by viewModel()
    var intentFilter: IntentFilter? = null

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == broadcastStringForAction) {
                if (intent.getBooleanExtra(onlineStatusKey, false)) {
                    viewModel.syncOfflineData()
                } else {
                    viewModel.fetchCarsData()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startConnectivityService()

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
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White
                            )
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

        viewModel.exception.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToAddCar() {
        startActivity(Intent(this, AddCarActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun startConnectivityService() {
        intentFilter = IntentFilter()
        intentFilter?.addAction(broadcastStringForAction)
        val serviceIntent = Intent(this, InternetService::class.java)
        startService(serviceIntent)
    }

    companion object {
        const val car_key = "car"
        const val broadcastStringForAction = "checkinternet"
    }
}
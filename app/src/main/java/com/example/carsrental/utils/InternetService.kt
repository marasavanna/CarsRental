package com.example.carsrental.utils

import android.app.Service
import android.content.Intent
import android.os.*
import com.example.carsrental.view.activity.CarsListActivity

class InternetService : Service() {

    private var networkManager: CarNetworkManager? = null


    private val periodicUpdate: Runnable = Runnable {
        networkManager?.let {
            val intent = Intent()
            intent.action = CarsListActivity.broadcastStringForAction
            intent.putExtra(onlineStatusKey, it.isNetworkAvailable())
            sendBroadcast(intent)
        }
        countDownTimer.start()
    }
    private val countDownTimer = object : CountDownTimer(2000L, 1) {
        override fun onTick(millisUntilFinished: Long) {

        }

        override fun onFinish() {
            periodicUpdate.run()
        }

    }


    override fun onCreate() {
        super.onCreate()
        networkManager = CarNetworkManager(applicationContext)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        countDownTimer.start()
        return START_STICKY
    }


    companion object {
        const val onlineStatusKey = "onlineStatus"
    }
}
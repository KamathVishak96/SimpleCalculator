package com.example.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class BoundService : Service() {

    private val binder = LocalBinder()

    private val generator = Random()

    val randomNumber: Int get() = generator.nextInt(100)


    override fun onBind(intent: Intent?): IBinder? = binder


    inner class LocalBinder : Binder() {
        fun getService(): BoundService = this@BoundService
    }

}
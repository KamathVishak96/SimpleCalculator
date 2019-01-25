package com.example.services

import android.app.Service
import android.content.Intent
import android.os.*
import timber.log.Timber

class ServiceClass : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    inner class ServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message?) {
            Thread.sleep(5000)
            if (msg?.arg1 != null)
                stopSelf(msg.arg1)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        HandlerThread("service", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)

        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceHandler?.obtainMessage()?.also {
            it.arg1 = startId
            serviceHandler?.sendMessage(it)
        }

        return START_STICKY
    }
    override fun onDestroy() {
        Timber.i("onDestroy: Destroyed")
    }
}
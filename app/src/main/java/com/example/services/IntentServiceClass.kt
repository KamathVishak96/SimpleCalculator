package com.example.services

import android.content.Intent
import android.support.v4.app.JobIntentService
import timber.log.Timber

class IntentServiceClass : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        Timber.i("onHandleWork: ${intent.dataString}")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.i("onStartCommand: Started")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy: Stopped")
    }

}
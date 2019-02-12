package com.example

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.net.ConnectivityManager
import android.content.IntentFilter
import com.example.utils.extensions.toast


abstract class BaseActivity: AppCompatActivity() {

    val networkChangeReceiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            toast("Network Changed")
            networkChanged(true)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onResume() {
        super.onResume()

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)
    }

    abstract fun networkChanged(isConnected: Boolean)


    override fun onPause() {
        super.onPause()

        unregisterReceiver(networkChangeReceiver)
    }
}
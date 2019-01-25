package com.example.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.R
import kotlinx.android.synthetic.main.activity_service.*
import org.w3c.dom.Text
import timber.log.Timber

class ServiceActivity : AppCompatActivity() {

    private lateinit var serviceIntent: Intent

    private lateinit var mService: BoundService
    private var mBound: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }

        serviceIntent = Intent().apply {
            putExtra("SERVICE", "SERVICEDATA")
        }



        btnStartIntentService.setOnClickListener {
            startService(Intent(this, IntentServiceClass::class.java))
        }

        btnStopIntentService.setOnClickListener {
            stopService(Intent(this, IntentServiceClass::class.java))
        }

        btnStartService.setOnClickListener {
            startService(Intent(this, ServiceClass::class.java))
        }

        btnStopService.setOnClickListener {
            stopService(Intent(this, ServiceClass::class.java))
        }

        btnGetNum.setOnClickListener {

            onButtonClick(it as TextView)
        }

    }

    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as BoundService.LocalBinder
            mService = binder.getService()
            mBound = true
            Timber.i("onServiceConnected: mBound = $mBound")
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
            Timber.i("onServiceDisconnected: mBound = $mBound")
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart ")

    }

    override fun onStop() {
        super.onStop()
        if(mBound) {
            unbindService(mConnection)
            mBound = false
        }
    }

    private fun onButtonClick(v: TextView) {
        Timber.d("onButtonClick: Outside if")
        if (mBound) {
            Timber.d("onButtonClick: inside if")
            v.text = mService.randomNumber.toString()
        }
    }
}
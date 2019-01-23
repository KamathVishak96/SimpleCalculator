package com.example.firebasetest

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(msg: RemoteMessage?) {
        Intent(this, FirebaseUserFirestoreActivity::class.java).apply {

            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(0,NotificationCompat.Builder(this@FirebaseMessagingService)
                .setContentTitle("Title")
                .setContentText(msg?.notification?.body)
                .setAutoCancel(true)
                .setContentIntent(
                    PendingIntent.getActivity(
                        this@FirebaseMessagingService,
                        0,
                        this,
                        PendingIntent.FLAG_ONE_SHOT
                    )
                ).build())


        }
        if (msg?.data != null) {
            Timber.d("onMessageReceived: ${msg.data.toString()}")
        }
    }
}

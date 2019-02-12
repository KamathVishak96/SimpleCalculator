package com.example.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.R
import java.util.*

class WorkerClassNotify(val context: Context, val params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
                createNotificationChannel(
                    NotificationChannel(
                        CHANNEL_ID,
                        "channel1",
                        NotificationManager.IMPORTANCE_DEFAULT
                    ).apply {
                        description = "Channel_Description"
                    })
            }
        }

        for(i in 0..10) {
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                i, NotificationCompat.Builder(
                    context,
                    i.toString()
                ).let {

                    it.setSmallIcon(R.drawable.notification_icon_background)
                    it.setContentTitle("Notification")
                    it.setContentText("${i}")
                    it.priority = NotificationCompat.PRIORITY_DEFAULT
                    it.build()
                })
            Thread.sleep(1000)
        }
        return Result.success()
    }

    companion object {
        private const val CHANNEL_ID: String = "1"


    }

}
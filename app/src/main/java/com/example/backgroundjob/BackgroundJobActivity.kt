package com.example.backgroundjob

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import com.example.R
import kotlinx.android.synthetic.main.activity_background_job.*
import timber.log.Timber
import java.util.*

class BackgroundJobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_job)

        (getSystemService(Context.ALARM_SERVICE) as AlarmManager)
            .set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 2 * 1000,
                Intent(this, AlarmReceiver::class.java).let {
                    PendingIntent.getBroadcast(this, 0, it, 0)
                })

        btnStartJob.setOnClickListener {
            if (JobScheduler.RESULT_SUCCESS == (getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler).schedule(
                    JobInfo.Builder(123, ComponentName(this, JobService::class.java))
                        .setRequiresCharging(true)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                        .setPersisted(false)
                        .setPeriodic(900000)
                        .build()
                )
            ) {
                Timber.i("scheduleJob: JOB SCHEDULED")
            } else {
                Timber.i("scheduleJob: JOB FAILED")
            }
        }

        btnEndJob.setOnClickListener {
            (getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler).cancel(123)
            Timber.e("cancelJob: CANCELLED")
        }
    }
}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.i("onReceive: Alarm Went off at ${Date().time}")
    }

}

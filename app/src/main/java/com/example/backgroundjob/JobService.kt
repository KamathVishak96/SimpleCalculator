package com.example.backgroundjob

import android.app.job.JobParameters
import android.app.job.JobService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class JobService : JobService() {

    var jobCancelled = false
    var job: Job? = null


    override fun onStopJob(params: JobParameters?): Boolean {
        Timber.i("onStartJob: Cancelled BEfore Completion")
        jobCancelled = true
        return true

    }

    private fun bgWork(params: JobParameters?) {
        /*Thread(Runnable {
            for(j in 0..10)
            {
                if(jobCancelled)
                    return@Runnable
                Thread.sleep(1000)

                Timber.i("bgWork: $j")
            }

            Timber.i("bgWork: Finished")
            jobFinished(params, false)
        }).start()*/
        job = GlobalScope.launch {
            for (j in 0..10) {
                if (jobCancelled)
                    return@launch
                delay(3000)

                Timber.i(" ${Thread.currentThread()} $j")
            }
            Timber.i(" ${Thread.currentThread()} Finished")
            jobFinished(params, false)
        }

        for (j in 0..10) {
            if (jobCancelled)
                return
            Thread.sleep(3000)

            Timber.i("${Thread.currentThread()}: $j")
        }
        Timber.i("bgWork main thread: Finished")


    }

    override fun onStartJob(params: JobParameters?): Boolean {
        bgWork(params)

        return true

    }
}
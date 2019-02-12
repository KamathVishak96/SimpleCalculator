package com.example.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.utils.extensions.toast

class WorkerClassToast(val context: Context, params: WorkerParameters): Worker(context, params){
    override fun doWork(): Result {
        /*for(i in 1..10){
            context.toast("i")
            Thread.sleep(1000)
        }*/

        return Result.success()
    }

}
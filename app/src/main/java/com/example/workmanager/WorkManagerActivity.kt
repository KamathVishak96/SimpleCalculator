package com.example.workmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.R
import kotlinx.android.synthetic.main.activity_work_manager.*

class WorkManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)


        OneTimeWorkRequest.Builder(WorkerClassNotify::class.java).build()
        llWorkManager.let {
            it.addView(Button(this).apply {
                text = "NOTIFY"
                id = 1000+2
                gravity = Gravity.CENTER_HORIZONTAL
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                setOnClickListener {
                    WorkManager.getInstance().enqueue(OneTimeWorkRequest.Builder(WorkerClassNotify::class.java).build())
                }
            })

            /*it.addView(Button(this).apply {
                text = "TOAST"
                id = 1000+1
                gravity = Gravity.CENTER_HORIZONTAL
                setOnClickListener {
                    WorkManager.getInstance().enqueue(OneTimeWorkRequest.Builder(WorkerClassToast::class.java).build())
                }
            })*/

            /*
            it.addView(Button(this).apply {
                text = "Get Status"
                gravity = Gravity.CENTER_HORIZONTAL
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                setOnClickListener {
                    WorkManager.getInstance().getWorkInfoByIdLiveData(work.id)
                        .observe()
                }
            })*/



        }


    }
}
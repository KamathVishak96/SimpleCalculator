package com.example.gestures

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MotionEvent
import com.example.R
import com.example.mainactivity.MainActivity
import com.example.utils.extensions.toast
import kotlinx.android.synthetic.main.activity_gestures.*

class GesturesActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    val activePointerId = MotionEvent.INVALID_POINTER_ID

    override fun onShowPress(e: MotionEvent?) {
        tvGestureEvent.text = "Press"

    }

    private lateinit var detector: GestureDetectorCompat


    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        tvGestureEvent.text = "SingleTapUp"
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        tvGestureEvent.text = "$e"
        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        tvGestureEvent.text = "$e1 $e2"
        toast("Fling")
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        tvGestureEvent.text = "$e1 $e2"
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        tvGestureEvent.text = "$e"

    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        tvGestureEvent.text = "$e"
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        tvGestureEvent.text = "$e"
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        tvGestureEvent.text = "$e"
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestures)

        detector = GestureDetectorCompat(this, this)
        detector.setOnDoubleTapListener(this)



        btnNotify.setOnClickListener {


            val intent = Intent(this, GesturesActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
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
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(CHANNEL_ID.toInt(), NotificationCompat.Builder(this@GesturesActivity, CHANNEL_ID).let {

                it.setSmallIcon(R.drawable.notification_icon_background)
                it.setContentTitle("CLicked")
                it.setContentText("Button CLicked")
                it.setContentIntent(PendingIntent.getActivity(this@GesturesActivity, 0, intent, 0))
                it.setStyle(NotificationCompat.BigTextStyle().bigText(" vkufffytcghvytftftytfytfytfytftfyfytdyrfcgfjcciyrcrtycffctrdtdtdmjtxurxrttrrtrxfrxrrikyhhydhyfxfxztxycytftyvkufffytcghvytftftytfytfytfytftfyfytdyrfcgfjcciyrcrtycffctrdtdtdmjtxurxrttrrtrxfrxrrikyhhydhyfxfxztxycytfty"))
                it.priority = NotificationCompat.PRIORITY_DEFAULT
                it.build()
            })

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                tvGestureEvent.text = "Down"
                true
            }
            MotionEvent.ACTION_MOVE -> {
                tvGestureEvent.text = "Moved"
                true
            }
            MotionEvent.ACTION_UP -> {
                tvGestureEvent.text = "Up"
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                tvGestureEvent.text = "Cancelled"
                true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                tvGestureEvent.text = "Outside"
                true
            }
            else -> {
                false
            }
        }
    }


    companion object {
        private const val CHANNEL_ID = "1"
    }
}

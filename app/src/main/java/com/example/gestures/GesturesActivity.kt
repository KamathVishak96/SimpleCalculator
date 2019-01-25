package com.example.gestures

import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MotionEvent
import com.example.R
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


}

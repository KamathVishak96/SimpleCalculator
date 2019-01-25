package com.example.utils.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.Toast
import com.example.R
import kotlinx.android.synthetic.main.activity_main.view.*
import timber.log.Timber

class CustomButton(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private lateinit var paint: Paint
    private lateinit var rectf: RectF
    var buttonColor: Int = 0
    lateinit var typeArray: TypedArray
    private var buttonSizeHorizontal = 0
    private var buttonSizeVertical = 0


    init{
        initialize(attrs)
    }

    private fun initialize(set: AttributeSet?) {
        Timber.d("initialize() called with: set = [$set]")
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        rectf = RectF()

        if (set == null)
            return
        typeArray = context.obtainStyledAttributes(set, R.styleable.CustomButton)
        buttonColor =
                typeArray.getColor(R.styleable.CustomButton_button_color, resources.getColor(R.color.buttonColorDark))
        buttonSizeHorizontal =
                typeArray.getDimensionPixelSize(R.styleable.CustomButton_button_size_horizontal, HORIZONTAL_SIZE_DEFAULT)
        buttonSizeVertical =
                typeArray.getDimensionPixelSize(R.styleable.CustomButton_button_size_vertical, VERTICAL_SIZE_DEFAULT)
       // typeArray.recycle()
    }


    override fun onDraw(canvas: Canvas?) {
        rectf.apply {
            left = 0f
            right = left + buttonSizeHorizontal
            top = 0f
            bottom = top + buttonSizeVertical
        }

        paint.color = buttonColor
        canvas?.drawRoundRect(rectf, 20f, 20f, paint)
    }

    private val myListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
    }
    private val mDetector: GestureDetector = GestureDetector(context, myListener)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        performClick()
        return mDetector.onTouchEvent(event).let { res ->
            if (!res) {
                if (event?.actionMasked == MotionEvent.ACTION_MOVE) {
                    customV.buttonColor = typeArray.getColor(
                        R.styleable.CustomButton_button_color,
                        resources.getColor(R.color.buttonColorLight)
                    )

                    true
                } else false
            } else false
        }
    }



    override fun onFinishInflate() {
        super.onFinishInflate()
        Timber.i("onFinishInflate: ")
    }

    companion object {
        const val HORIZONTAL_SIZE_DEFAULT = 100
        const val VERTICAL_SIZE_DEFAULT = 50
    }

}
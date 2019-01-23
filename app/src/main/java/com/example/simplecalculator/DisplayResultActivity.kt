package com.example.simplecalculator

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.R
import kotlinx.android.synthetic.main.activity_display_result.*

class DisplayResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_result)

        val msg = intent.getStringExtra(EXTRA)

        val textView = findViewById<TextView>(R.id.textView).apply {
            text = msg

        }
        var currentColor: Int = Color.rgb(0,0,0)
        toggleButton.textOff = "BLUE"
        toggleButton.textOn = "GREEN"
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentColor = Color.rgb(0,0,255)
                textView.setTextColor(currentColor)
            }
            else {
                currentColor = Color.rgb(0,255,0)
                textView.setTextColor(currentColor)
            }
        }
        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                textView.setTextColor(currentColor)
            else
                textView.setTextColor(Color.rgb(255,255,255))
        }



    }
}

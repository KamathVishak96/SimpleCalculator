package com.example.sharing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R
import kotlinx.android.synthetic.main.activity_sharing.*


class SharingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharing)

        videoView2.setOnClickListener {
            videoView2.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=GDkL2uEn8cg"))
            videoView2.start()
        }

        btnShareTest.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }
            startActivity(sendIntent)
        }
    }
}

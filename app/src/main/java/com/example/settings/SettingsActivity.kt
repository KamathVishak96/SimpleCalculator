package com.example.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R
import kotlinx.android.synthetic.main.activity_settings.*

@Suppress("DEPRECATION")
class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        llSettings.setBackgroundColor(resources.getColor(R.color.bgColorPrimary))


        supportFragmentManager.beginTransaction()
            .replace(R.id.llSettings, SettingsFragment())
            .commit()

    }

}

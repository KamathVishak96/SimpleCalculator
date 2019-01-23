package com.example.fragmentactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.example.R
import kotlinx.android.synthetic.main.activity_fragment_run_time.*

class FragmentRunTIme : AppCompatActivity() {

    companion object {
        lateinit var fragmentManager: FragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_run_time)

        FragmentRunTIme.fragmentManager = supportFragmentManager
        if (flFragmentContainer != null) {
            if (savedInstanceState != null)
                return
            val fragmentTransaction = FragmentRunTIme.fragmentManager.beginTransaction()

            fragmentTransaction.add(R.id.flFragmentContainer, HomeFragment(), null)
            fragmentTransaction.commit()
        }


    }
}

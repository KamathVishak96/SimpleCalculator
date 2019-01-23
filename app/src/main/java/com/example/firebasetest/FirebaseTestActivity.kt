package com.example.firebasetest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R

/**
 * A login screen that offers login via email/password.
 */
class FirebaseTestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_test)

        supportFragmentManager.beginTransaction()
            .add(R.id.flFirebaseTest, FirebaseRegisterTestFragment())
            .commit()

    }


}

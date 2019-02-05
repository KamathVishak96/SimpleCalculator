package com.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_camera_kit.*

class CameraKitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_kit)
    }

    override fun onStart() {
        super.onStart()
        cameraKitViewRear.onStart()
        cameraKitViewFront.onStart()
    }

    override fun onResume() {
        super.onResume()
        cameraKitViewRear.onResume()
        cameraKitViewFront.onResume()
    }

    override fun onPause() {
        cameraKitViewRear.onPause()
        cameraKitViewFront.onPause()
        super.onPause()

    }

    override fun onStop() {
        cameraKitViewRear.onStop()
        cameraKitViewFront.onStop()
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitViewRear.onRequestPermissionsResult(requestCode, permissions, grantResults)

        cameraKitViewFront.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

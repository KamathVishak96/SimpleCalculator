package com.example.utils.extensions

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.R

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    shouldAddToBackStack: Boolean = false,
    containerId: Int = R.id.flContent,
    tag: String = fragment::class.java.simpleName
) {
    supportFragmentManager.beginTransaction().run {
        replace(containerId, fragment, tag)
        if (shouldAddToBackStack) addToBackStack(tag)
        commit()
    }
}
package com.example.fragmentactivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import kotlinx.android.synthetic.main.fragment_test_two.*
import kotlinx.android.synthetic.main.fragment_test_two.view.*
import timber.log.Timber

class TestTwoFragment : Fragment() {

    lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            tvFragTestTwo.text = savedInstanceState.getString(KEY_TEXTVIEW_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (savedInstanceState != null) {
            tvFragTestTwo.text = savedInstanceState.getString(KEY_TEXTVIEW_ID)
        }


        rootView = inflater.inflate(R.layout.fragment_test_two, container, false)

        arguments?.getString("msg").run {
            if (this != null)
                rootView.tvFragTestTwo.text = this
            else
                rootView.tvFragTestTwo.text = "NO BUTTONS CLICKED"
        }



        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.i("onSaveInstance")
        outState.putString(KEY_TEXTVIEW_ID, tvFragTestTwo.text.toString())
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val TAG = "com.example.fragmentactivity.fragment_two"
        const val KEY_TEXTVIEW_ID = "com.example.fragmentactivity.tv"
    }
}
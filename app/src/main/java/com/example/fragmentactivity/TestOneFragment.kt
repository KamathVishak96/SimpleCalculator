package com.example.fragmentactivity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import kotlinx.android.synthetic.main.fragment_test_one.view.*

class TestOneFragment : Fragment() {

        lateinit var listener: OnButtonClickListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val rootView = inflater.inflate(R.layout.fragment_test_one, container, false)

        rootView.btnFragTestOneMessageOne.setOnClickListener {
            listener.onButtonCLickListener("MESSAGE ONE")
        }

        rootView.btnFragTestOneMessageTwo.setOnClickListener {
            listener.onButtonCLickListener("MESSAGE TWO")
        }

        return rootView

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnButtonClickListener) {
            listener = context
        }

    }

    interface OnButtonClickListener {
        fun onButtonCLickListener(strMessage: String)
    }


    companion object {
        const val TAG = "FRAGMENT_ONE"
    }

}
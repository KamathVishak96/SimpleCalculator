package com.example.fragmentactivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import kotlinx.android.synthetic.main.fragment_one.view.*


class FragmentOne : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_one, container, false)

        view.btnFragOne.setOnClickListener {
            FragmentRunTIme.fragmentManager.run {
                this.beginTransaction().replace(R.id.flFragmentContainer, FragmentTwo(), null).commit()
            }
        }

        return view
    }


}

package com.example.fragmentactivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.btnChangeFragment.setOnClickListener {
            FragmentRunTIme.fragmentManager.run {
                this.beginTransaction().replace(R.id.flFragmentContainer, FragmentOne(), null).commit()
            }
        }

        return view
    }

}
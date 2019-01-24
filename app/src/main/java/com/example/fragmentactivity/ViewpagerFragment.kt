package com.example.fragmentactivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import kotlinx.android.synthetic.main.fragment_viewpager.view.*

class ViewpagerFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_viewpager, container, false)
        arguments?.takeIf {
            it.containsKey(KEY)
        }?.apply {
            rootView.tvViewPagerFragment.text = "View ${getInt(KEY)}"
        }
        return rootView
    }


}

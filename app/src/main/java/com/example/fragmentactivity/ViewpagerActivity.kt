package com.example.fragmentactivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.example.R
import kotlinx.android.synthetic.main.activity_viewpager.*

const val NUM_PAGES = 2
const val KEY = "string"

class ViewpagerActivity : FragmentActivity()                  {

    private lateinit var pager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)

        pager = viewPager
        pager.adapter = ViewPagerAdapter(supportFragmentManager)
    }

    override fun onBackPressed() {
        if (pager.currentItem == 0)
            super.onBackPressed()
        else
            pager.currentItem--
    }

    private inner class ViewPagerAdapter(val fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(index: Int): Fragment {
            val fragmentItem = ViewpagerFragment()
            fragmentItem.arguments = Bundle().apply {
                putInt(KEY, index + 1)
            }
            return fragmentItem
        }

        override fun getCount(): Int = NUM_PAGES

        override fun getPageTitle(position: Int): CharSequence? {
            return "Page ${position + 1}"
        }
    }


}

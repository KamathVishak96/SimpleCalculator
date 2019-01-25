package com.example.fragmentactivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.example.R
import kotlinx.android.synthetic.main.activity_viewpager.*

const val NUM_PAGES = 2
const val KEY = "string"

class ViewpagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)

        setSupportActionBar(tbViewpager)
        tlViewpager.setupWithViewPager(viewPager)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit=1

    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0)
            super.onBackPressed()
        else
            viewPager.currentItem--
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

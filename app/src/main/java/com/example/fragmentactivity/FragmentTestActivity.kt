package com.example.fragmentactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R
import kotlinx.android.synthetic.main.activity_fragment_test.*
import kotlinx.android.synthetic.main.fragment_test_two.view.*
import timber.log.Timber

class FragmentTestActivity : AppCompatActivity(), TestOneFragment.OnButtonClickListener {


    private val testOneFragment = TestOneFragment()
    private val testTwoFragment = TestTwoFragment()

    lateinit var currentFragmentName: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.flFragTest, testOneFragment, "fragmentone")
                .addToBackStack("fragmentone").commit()
            currentFragmentName = "fragmentone"
        } else {

            if (savedInstanceState.getString("savedfragmentname")?.toString().equals("fragmenttwo")) {

                createFragmentTwo()
            } else
                createFragmentOne()

        }


        btnTestFragOne.setOnClickListener {
            createFragmentOne()
        }

        btnTestFragTwo.setOnClickListener {
            createFragmentTwo()
        }
    }

    private var fragmentName: String? = null


    override fun onPause() {
        super.onPause()
        if (supportFragmentManager.backStackEntryCount > 0)
            fragmentName =
                    supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name

        while (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStackImmediate()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)


        if (outState != null) {
            outState.putString("savedfragmentname", currentFragmentName)

        }

    }

    private fun createFragmentTwo() {
        if (supportFragmentManager.findFragmentByTag("fragmenttwo") == null)
            supportFragmentManager.beginTransaction().replace(
                R.id.flFragTest,
                testTwoFragment,
                "fragmenttwo"
            ).addToBackStack("fragmenttwo")
                .commit()
        else
            supportFragmentManager.beginTransaction().replace(
                R.id.flFragTest,
                testTwoFragment,
                "fragmenttwo"
            ).commit()
        currentFragmentName = "fragmenttwo"

        Timber.i("${testTwoFragment.view?.tvFragTestTwo?.text}")
    }

    private fun createFragmentOne() {
        supportFragmentManager.beginTransaction().replace(R.id.flFragTest, testOneFragment)
            .commit()
        currentFragmentName = "fragmentone"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStackImmediate()
        if (supportFragmentManager.backStackEntryCount < 1)
            finish()
    }

    override fun onButtonCLickListener(strMessage: String) {
        val bundle = Bundle()
        bundle.putString("msg", strMessage)
        testTwoFragment.arguments = bundle
    }

}
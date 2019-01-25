package com.example.mainactivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import com.example.R
import com.example.contentprovider.ContentProviderActivity
import com.example.database.DatabaseActivity
import com.example.firebasetest.FirebaseTestActivity
import com.example.firebasetest.FirebaseUserActivity
import com.example.firebasetest.ProfileTestActivity
import com.example.fragmentactivity.FragmentActivityXML
import com.example.fragmentactivity.FragmentRunTIme
import com.example.fragmentactivity.FragmentTestActivity
import com.example.fragmentactivity.ViewpagerActivity
import com.example.gestures.GesturesActivity
import com.example.moviedetails.MoviesActivity
import com.example.moviedetails.MoviesCardViewActivity
import com.example.permissionsandjson.PermissionTestActivity
import com.example.recyclerview.RecyclerViewActivity
import com.example.services.ServiceActivity
import com.example.sharing.SharingActivity
import com.example.simplecalculator.CalculatorActivity
import com.example.utils.extensions.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {


    private val listOfActivities by lazy {
        listOf(
            ActivityNames("Calculator", CalculatorActivity::class.java),
            ActivityNames("Recycler", RecyclerViewActivity::class.java),
            ActivityNames("FrgXML", FragmentActivityXML::class.java),
            ActivityNames("FrgRT", FragmentRunTIme::class.java),
            ActivityNames("FrgTest", FragmentTestActivity::class.java),
            ActivityNames("Movies", MoviesActivity::class.java),
            ActivityNames("MoviesCard", MoviesCardViewActivity::class.java),
            ActivityNames("Permission", PermissionTestActivity::class.java),
            ActivityNames("Share", SharingActivity::class.java),
            ActivityNames("DB", DatabaseActivity::class.java),
            ActivityNames("Content Provider", ContentProviderActivity::class.java),
            ActivityNames(
                name = if (FirebaseAuth.getInstance().currentUser != null) {
                    "profile"
                } else {
                    "firebase"
                }, klass = if (FirebaseAuth.getInstance().currentUser != null) {
                    ProfileTestActivity::class.java
                } else {
                    FirebaseTestActivity::class.java
                }
            ),
            ActivityNames("FireStore", FirebaseUserActivity::class.java),
            ActivityNames("viewpager", ViewpagerActivity::class.java),
            ActivityNames("Gestures", GesturesActivity::class.java),
            ActivityNames("Services", ServiceActivity::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


        rvActivities?.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            val activitiesAdapter = RecyclerAdapter {
                onButtonItemClick(it)
            }
            activitiesAdapter.setActivities(listOfActivities)
            adapter = activitiesAdapter
        }

        Timber.d("onCreate: $customV")

        customV.apply {
            setOnClickListener {
                toast("Clicked")
            }
        }
    }

    private fun onButtonItemClick(activity: ActivityNames) {
        startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
    }
}

data class ActivityNames(val name: String, val klass: Class<*>, val extras: Bundle? = null)

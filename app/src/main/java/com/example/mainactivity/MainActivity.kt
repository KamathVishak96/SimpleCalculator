package com.example.mainactivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import com.example.ContentProviderActivity
import com.example.R
import com.example.SharingActivity
import com.example.database.DatabaseActivity
import com.example.firebasetest.FirebaseTestActivity
import com.example.firebasetest.FirebaseUserActivity
import com.example.firebasetest.ProfileTestActivity
import com.example.fragmentactivity.FragmentActivityXML
import com.example.fragmentactivity.FragmentRunTIme
import com.example.fragmentactivity.FragmentTestActivity
import com.example.fragmentactivity.ViewpagerActivity
import com.example.moviedetails.MoviesActivity
import com.example.moviedetails.MoviesCardViewActivity
import com.example.permissionsandjson.PermissionTestActivity
import com.example.recyclerview.RecyclerViewActivity
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
            ActivityNames("Firebase", FirebaseTestActivity::class.java),
            ActivityNames("FireStore", FirebaseUserActivity::class.java),
            ActivityNames("viewpager", ViewpagerActivity::class.java)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




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
            /*setOnTouchListener { _, event ->
                Timber.i("onCreate: ${event.action == MotionEvent.ACTION_DOWN}")
                if (event.action == MotionEvent.ACTION_DOWN) {
                    this.buttonColor = this.typeArray.getColor(
                        R.styleable.CustomButton_button_color,
                        resources.getColor(R.color.buttonColorLight)
                    )
                    true
                } else false

            }*/
        }

/*
        calculatorButton.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java).apply {
                putExtra(KEY, "Hello")
            }
            startActivity(intent)
        }

        recyclerButton.setOnClickListener {
            val intent = Intent(this, RecyclerViewActivity::class.java).apply {
                putExtra(KEY1, "Hello")
            }
            startActivity(intent)

        }

        btnFrgXml.setOnClickListener {
            val intent = Intent(this, FragmentActivityXML::class.java).apply {
                putExtra("fragmentxml", "Hello")
            }
            startActivity(intent)
        }

        btnFrgRT.setOnClickListener {
            val intent = Intent(this, FragmentRunTIme::class.java).apply {
                putExtra("fragmentrt", "Hello")
            }
            startActivity(intent)
        }

        btnFragmentTest.setOnClickListener {
            val intent = Intent(this, FragmentTestActivity::class.java).apply {
                putExtra("testfragment", "Hello")
            }
            startActivity(intent)
        }

        btnMovieList.setOnClickListener {
            val intent = Intent(this, MoviesActivity::class.java).apply {
                putExtra("movielist", "hello")
            }
            startActivity(intent)
        }

        btnPermissionTest.setOnClickListener {
            startActivity(Intent(this, PermissionTestActivity::class.java).apply {
                putExtra("permission", "Hello")
            })
        }

        btnShareActivity.setOnClickListener {
            startActivity(Intent(this, SharingActivity::class.java).apply {
                putExtra("sharing", "Hello")
            })
        }

        btnDBActivity.setOnClickListener {
            startActivity(Intent(this, DatabaseActivity::class.java).apply {
                putExtra("Database", "Hello")
            })
        }

        btnContentProviderActivity.setOnClickListener {
            startActivity(Intent(this, ContentProviderActivity::class.java).putExtra("CP", "hello"))
        }

        btnFirebaseTest.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(this, ProfileTestActivity::class.java).putExtra("profile", "hello"))
            } else {
                startActivity(Intent(this, FirebaseTestActivity::class.java).putExtra("firebase", "hello"))
            }
        }

        btnFireBaseCreateUser.setOnClickListener {
            startActivity(Intent(this, FirebaseUserActivity::class.java).putExtra("firebasecreateuser", "hello"))
        }

        btnFireStore.setOnClickListener {
            startActivity(Intent(this, FirebaseUserFirestoreActivity::class.java).putExtra("firestore", "hello"))
        }

        btnMovieCardView.setOnClickListener {
            startActivity(Intent(this, MoviesCardViewActivity::class.java).putExtra("moviescv", "hello"))
        }*/
    }


    private fun onButtonItemClick(activity: ActivityNames) {

        startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
        /*when (activity.name) {

            "Calculator" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "Recycler" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "FrgXML" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "FrgRT" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "FrgTest" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "Movies" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "MoviesCard" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "Permission" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "Share" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "DB" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "Content Provider" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "Profile Test" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }

            "firebase" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "Firebase" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            "FireStore" -> {
                startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
            }
            else -> {

            }
        }*/
    }
}

data class ActivityNames(val name: String, val klass: Class<*>, val extras: Bundle? = null)

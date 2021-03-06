package com.example.mainactivity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.example.BaseActivity
import com.example.CameraKitActivity
import com.example.R
import com.example.backgroundjob.BackgroundJobActivity
import com.example.contentprovider.ContentProviderActivity
import com.example.database.DatabaseActivity
import com.example.dependencyinjection.DaggerActivity
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
import com.example.mvvm.vm.MvvmTwoActivity
import com.example.mvvm.vmandlivedata.MvvmActivity
import com.example.permissionsandjson.PermissionTestActivity
import com.example.recyclerview.RecyclerViewActivity
import com.example.retrofit.RetrofitActivity
import com.example.services.ServiceActivity
import com.example.settings.SettingsActivity
import com.example.sharing.SharingActivity
import com.example.simplecalculator.CalculatorActivity
import com.example.utils.extensions.toast
import com.example.workmanager.WorkManagerActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : BaseActivity() {

    override fun networkChanged(isConnected: Boolean) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsPref = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean("switch_theme", false)
        Timber.i("onCreate: $settingsPref")
        GlobalScope.launch {
            if (settingsPref) {
                setTheme(R.style.AppThemeDark)
            } else
                setTheme(R.style.AppTheme)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setSupportActionBar(tbCalculator)

        btnCustom.apply {
            setOnClickListener {
                toast("Clicked")
                moveButton(1, it)
            }

            setOnLongClickListener {
                toast("Long CLicked")
                moveButton(0, it)
                true
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark)
        } else setTheme(R.style.AppTheme)

        val listOfActivities by lazy {
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
                        "Firebase (Logged In as ${FirebaseAuth.getInstance().currentUser?.displayName})"
                    } else {
                        "Firebase(Not Logged In)"
                    }, klass = if (FirebaseAuth.getInstance().currentUser != null) {
                        ProfileTestActivity::class.java
                    } else {
                        FirebaseTestActivity::class.java
                    }
                ),
                ActivityNames("FireStore", FirebaseUserActivity::class.java),
                ActivityNames("viewpager", ViewpagerActivity::class.java),
                ActivityNames("Gestures", GesturesActivity::class.java),
                ActivityNames("Services", ServiceActivity::class.java),
                ActivityNames("BG JOB", BackgroundJobActivity::class.java),
                ActivityNames("Dagger", DaggerActivity::class.java),
                ActivityNames("CameraKit", CameraKitActivity::class.java),
                ActivityNames("Retrofit", RetrofitActivity::class.java),
                ActivityNames("MVVM", MvvmActivity::class.java),
                ActivityNames("MVVM Two", MvvmTwoActivity::class.java),
                ActivityNames("Work Manager", WorkManagerActivity::class.java)
            )
        }

        rvActivities?.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

            val activitiesAdapter = RecyclerAdapter {
                onItemClick(it)
            }

            activitiesAdapter.setActivities(listOfActivities)
            adapter = activitiesAdapter
        }
    }

    private fun moveButton(offsetMultiplier: Int, button: View) {
        ObjectAnimator.ofFloat(
            button,
            "translationX",
            offsetMultiplier * (resources.displayMetrics.widthPixels - button.width).toFloat()
        )
            .apply {
                start()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onItemClick(ActivityNames("Settings", SettingsActivity::class.java))
        return true
    }

    private fun onItemClick(activity: ActivityNames) {
        startActivity(Intent(this, activity.klass).putExtra(activity.name, ""))
    }
}

data class ActivityNames(val name: String, val klass: Class<*>, val extras: Bundle? = null)

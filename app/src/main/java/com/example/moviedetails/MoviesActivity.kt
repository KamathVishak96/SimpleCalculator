package com.example.moviedetails

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.example.AppConstants
import com.example.BaseActivity
import com.example.R
import com.example.utils.delegates.SharedPrefDelegate
import com.example.utils.extensions.replaceFragment
import com.fondesa.kpermissions.extension.listeners
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_movies.*
import timber.log.Timber
import java.io.*

class MoviesActivity : BaseActivity(), MovieListFragment.EventListener,
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun networkChanged(isConnected: Boolean) {

    }

    private val movieListFragment by lazy { MovieListFragment() }
    private val movieDetailsFragment by lazy { MovieDetailsFragment() }

    private val sharedPreferences by lazy {
        getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    private val movieDetailsDialogFragment by lazy {
        MovieDetailsDialogFragment()
    }

    private lateinit var currentFragmentName: String
    private var recentTitle by SharedPrefDelegate(
        this,
        AppConstants.SharedPrefKeys.KEY_RECENTLY_CLICKED_MOVIE,
        "None"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movies)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (savedInstanceState == null) {

            replaceFragment(movieListFragment)
            currentFragmentName = MovieListFragment.KEY
        }

        setSupportActionBar(tbMovies)
        btnShowRecentTitle.setOnClickListener {
            toastRecentlyClicked()
        }

        btnShowRecentDetails.setOnClickListener {

            Toast.makeText(this, readCache(), Toast.LENGTH_SHORT).show()
            movieDetailsDialogFragment.arguments = Bundle().apply {
                putString(
                    KEY_DIALOG, Moshi.Builder().build()
                        .adapter(Movie::class.java)
                        .fromJson(readDetails(FILE_NAME))
                        .toString()
                )
            }
            movieDetailsDialogFragment.show(supportFragmentManager, DIALOG_TAG)
        }
    }


    override fun onStart() {
        super.onStart()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }


    override fun onStop() {
        super.onStop()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == AppConstants.SharedPrefKeys.KEY_RECENTLY_CLICKED_MOVIE) {
            toastRecentlyClicked()
        }
    }

    private fun toastRecentlyClicked() {
        Timber.d("toastRecentlyClicked() called")

        Toast.makeText(this, recentTitle, Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStackImmediate()
    }

    private lateinit var outputString: String

    override fun onMovieItemClick(movie: Movie) {
        Timber.d("onMovieItemClick() called with: movie = [$movie]")

        recentTitle = movie.title

        outputString = """Title: ${movie.title}
                Actor: ${movie.actor}
                Year: ${movie.year}
                Rating: ${movie.rating}"""

        //Writing the details
        write(
            filesDir,
            FILE_NAME,
            Moshi.Builder().build()
                .adapter<Movie>(Movie::class.java).toJson(movie)
        )

        //Writing the Cache
        write(cacheDir, CACHE_FILE_NAME, movie.rating.toString())

        //Write Private Files to external storage
        write(
            getExternalFilesDir("MovieDetails"),
            FILE_NAME,
            outputString
        )
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Permission required to write the file to External Storage")
                    .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ),
                            PERMISSION_WRITE_EXTERNAL
                        )
                    }
                    .setNegativeButton("CANCEL") { dialog: DialogInterface, _: Int ->
                        dialog.dismiss()
                    }.create().show()
            else
                permissionsBuilder(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                    .build().apply {
                        send()
                        listeners {
                            onAccepted {

                            }
                            onDenied {

                            }
                            onPermanentlyDenied {

                            }
                            onShouldShowRationale { strings, permissionNonce ->

                            }
                        }
                    }
            /*ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_WRITE_EXTERNAL
            )*/
        } else {
            write(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                FILE_NAME,
                outputString
            )
        }

        movieDetailsFragment.arguments = Bundle().apply {
            putParcelable(Movie.KEY, movie)
        }

        replaceFragment(movieDetailsFragment, true)
        currentFragmentName = MovieDetailsFragment.KEY
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_WRITE_EXTERNAL -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
                        write(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            FILE_NAME,
                            outputString
                        )
                    return
                }
            }
            else -> {
                return
            }
        }
    }

    private fun readCache(): String = read(FileInputStream(File(cacheDir, CACHE_FILE_NAME)))

    private fun readDetails(filename: String): String = read(openFileInput(filename))

    private fun write(directory: File?, fileName: String, content: String) {
        File(directory, fileName).run {
            createNewFile()
            mkdirs()

            FileOutputStream(this).run {
                write(content.toByteArray())
                flush()
                close()
            }
        }
    }

    private fun read(fileInputStream: FileInputStream): String {


        StringBuilder().run {
            BufferedReader(InputStreamReader(fileInputStream))
                .forEachLine {
                    this.append(it)
                }
            fileInputStream.close()
            return this.toString()

        }
    }

    companion object {
        const val KEY_DIALOG = "details_dialog_key"
        const val DIALOG_TAG = "Dialog"
        const val FILE_NAME = "Movie Details.txt"
        const val CACHE_FILE_NAME = "Cache.txt"
        const val PERMISSION_WRITE_EXTERNAL = 1
    }


}
package com.example.moviedetails

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Network
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.example.R
import com.example.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

open class NetworkChangeListenerFragment : Fragment() {
    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            super.onAvailable(network)
            activity?.toast("Changed")
        }

    }
}


class MovieDetailsFragment : NetworkChangeListenerFragment() {


    private lateinit var movie: Movie
    private var downloadStatus = false


    private var posterDialogFragment = PosterDialogFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = runBlocking {
        super.onViewCreated(view, savedInstanceState)

        val packageName = activity?.packageName

        arguments?.getParcelable<Movie>(Movie.KEY)?.run {

            movie = this

            tvTitle?.text = "Title: $title"
            tvActor?.text = "Actor: $actor"
            tvYear?.text = "Year: $year"
            tvRating?.text = "Rating: $rating"

            ivMoviePoster.setOnClickListener {


                /*if (!(context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                        .activeNetworkInfo.let {
                        it != null &&
                                it.isConnected
                    }
                ) {
                    val snackBar = Snackbar.make(view, "Not Connected", Snackbar.LENGTH_LONG)
                    snackBar.apply {
                        view.let {
                            (it.layoutParams as FrameLayout.LayoutParams).let { params ->
                                params.gravity = Gravity.TOP
                                it.layoutParams = params
                            }
                        }
                        downloadStatus = false
                        show()
                    }
                } else {

*/
                GlobalScope.launch {
                    val result = (RetrofitFactory.getInstance()?.create(PosterDownloadService::class.java)?.getImage(
                        when (title.replace(" ", "_").toLowerCase()) {
                            "civil_war" -> "5/53/Captain_America_Civil_War_poster.jpg"
                            "avengers" -> "f/f9/TheAvengers2012Poster.jpg"
                            "infinity_war" -> "4/4d/Avengers_Infinity_War_poster.jpg"
                            "aquaman" -> "3/3a/Aquaman_poster.jpg"
                            "age_of_ultron" -> "f/ff/Avengers_Age_of_Ultron_poster.jpg"
                            else -> ""
                        }
                    ))?.awaitResult()

                    when (result) {
                        is Result.Ok -> {
                            saveImage(
                                BitmapFactory.decodeStream(result.value.byteStream()),
                                title.replace(" ", "_").toLowerCase()
                            )
                            posterDialogFragment.arguments = Bundle().apply {
                                putString(KEY_FILE_NAME, title.replace(" ", "_").toLowerCase())
                            }
                            posterDialogFragment.show(activity?.supportFragmentManager, DIALOG_TAG)
                            return@launch
                        }
                        is Result.Error -> {
                            Timber.e("onViewCreated: ")
                        }
                        // Exception while request invocation
                        is Result.Exception -> {
                            Timber.e("onViewCreated: Exception")
                        }
                        else -> {

                        }
                    }
                }





                //}
            }

            Timber.d("onViewCreated: $packageName")
            Timber.d(
                "onViewCreated: ${resources.getIdentifier(
                    title.replace(" ", "_").toLowerCase(),
                    "drawable",
                    packageName
                )}"
            )

            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
                inSampleSize = 3
            }
            BitmapFactory.decodeResource(
                resources, resources.getIdentifier(
                    title.replace(" ", "_").toLowerCase(),
                    "drawable", packageName
                ), options
            )


            ivMoviePoster.setImageBitmap(
                decodeSampledBitmapFromResource(
                    resources,
                    resources.getIdentifier(
                        title.replace(" ", "_").toLowerCase(),
                        "drawable", packageName
                    ),
                    100,
                    100
                )
            )

            btnShareMovieDetails.setOnClickListener {
                shareMovie(this)
            }

        } ?: Toast.makeText(context, "movie is null", Toast.LENGTH_SHORT).show()


    }

    fun saveImage(resource: Bitmap, fileName: String): String? {
        val storageDir =
            File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/Posters")
        val savedImagePath: String?
        var success = true
        if (!storageDir.exists()) {
            Timber.d("saveImage: Directory Does not exist")
            success = storageDir.mkdirs()
            Timber.i("saveImage: Directory Created? $success")
        }
        if (success) {
            val imageFile = File(storageDir, "$fileName.jpg")
            savedImagePath = imageFile.absolutePath
            Timber.i("saveImage: SavedImagePath = $savedImagePath")


            try {
                val fileOutputStream = FileOutputStream(imageFile)
                resource.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            addPicToGallery(savedImagePath)

            return savedImagePath
        }
        return ""

    }

    private fun addPicToGallery(imagePath: String) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
    }

    private fun shareMovie(movie: Movie) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            with(movie) {
                putExtra(Intent.EXTRA_TEXT, "Title: $title\nActor: $actor\nYear: $year\nRating: $rating")
            }
            putExtra(Intent.EXTRA_SUBJECT, "The movie I watched")
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_item_share -> {
                if (::movie.isInitialized)
                    shareMovie(movie)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun decodeSampledBitmapFromResource(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, this)

            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false

            BitmapFactory.decodeResource(res, resId, this)
        }
    }

    companion object {
        const val KEY = "MovieDetailsFragment"
        const val DIALOG_TAG = "Dialog"
        const val KEY_FILE_NAME = "file_name"
    }
}

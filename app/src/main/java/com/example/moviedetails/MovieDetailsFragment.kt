package com.example.moviedetails

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.R
import kotlinx.android.synthetic.main.fragment_movie_details.*
import timber.log.Timber


class MovieDetailsFragment : Fragment() {



    private lateinit var movie: Movie

    private var posterDialogFragment = PosterDialogFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val packageName = activity?.packageName




        arguments?.getParcelable<Movie>(Movie.KEY)?.run {

            movie = this

            tvTitle?.text = "Title: $title"
            tvActor?.text = "Actor: $actor"
            tvYear?.text = "Year: $year"
            tvRating?.text = "Rating: $rating"

            ivMoviePoster.setOnClickListener {

                /*dialog.setContentView(R.layout.fragment_poster_dialog)
                dialog.setTitle("POSTER")*/

                posterDialogFragment.arguments = Bundle().apply {
                    putString(KEY_FILE_NAME, title.replace(" ", "_").toLowerCase())
                }
                posterDialogFragment.show(activity?.supportFragmentManager, DIALOG_TAG)

            }

            Timber.d("onViewCreated: $packageName")
            Timber.d(
                "onViewCreated: ${resources.getIdentifier(
                    title.replace(" ", "_").toLowerCase(),
                    "drawable",
                    packageName
                )}"
            )

            val options = BitmapFactory.Options().apply{
                inJustDecodeBounds = true
                inSampleSize = 3
            }
            BitmapFactory.decodeResource(resources, resources.getIdentifier(
                title.replace(" ", "_").toLowerCase(),
                "drawable", packageName
            ), options)
            val imageHeight: Int = options.outHeight
            val imageWidth: Int = options.outWidth
            val imageType: String = options.outMimeType

            ivMoviePoster.setImageBitmap(decodeSampledBitmapFromResource(
                resources,
                resources.getIdentifier(
                    title.replace(" ", "_").toLowerCase(),
                    "drawable", packageName
                ),
                100,
                100
            ))


            /*Glide.with(this@MovieDetailsFragment)
                .load(
                    resources.getIdentifier(
                        title.replace(" ", "_").toLowerCase(),
                        "drawable", packageName
                    )
                )
                .into(ivMoviePoster)*/
/*
            ivMoviePoster.setImageResource(
                resources.getIdentifier(
                    title.replace(" ", "_").toLowerCase(),
                    "drawable",
                    packageName
                )
            )*/

            btnShareMovieDetails.setOnClickListener {
                shareMovie(this)
            }

        } ?: Toast.makeText(context, "movie is null", Toast.LENGTH_SHORT).show()


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

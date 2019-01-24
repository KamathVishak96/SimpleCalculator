package com.example.moviedetails

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
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
        val options = BitmapFactory.Options().apply{
            inJustDecodeBounds = true
        }




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

            Glide.with(this@MovieDetailsFragment)
                .load(
                    resources.getIdentifier(
                        title.replace(" ", "_").toLowerCase(),
                        "drawable", packageName
                    )
                )
                .into(ivMoviePoster)
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

    companion object {
        const val KEY = "MovieDetailsFragment"
        const val DIALOG_TAG = "Dialog"
        const val KEY_FILE_NAME = "file_name"
    }
}

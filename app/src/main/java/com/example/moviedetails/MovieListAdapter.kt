package com.example.moviedetails

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import com.example.utils.extensions.toast
import kotlinx.android.synthetic.main.rv_movie_list_item.view.*

class MovieListAdapter(val activity: FragmentActivity, val onClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private var movieNames = emptyList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_movie_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieNames[position])
    }

    override fun getItemCount() = movieNames.size

    fun setMovieList(movieNameList: List<Movie>) {
        movieNames = movieNameList
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(movie: Movie) {
            with(itemView) {
                tvMovieName.text = movie.title
                civMovieListIcon.apply {
                    setImageBitmap(
                        decodeSampledBitmapFromResource(
                            resources,
                            resources.getIdentifier(
                                movie.title.replace(" ", "_").toLowerCase(),
                                "drawable",
                                activity.packageName
                            ),
                            100,
                            100
                        )
                    )

                    isLongClickable = true

                    setOnLongClickListener {

                        PosterDialogFragment().let {
                            it.arguments = Bundle().apply {
                                putString(KEY_FILE_NAME, movie.title.replace(" ", "_").toLowerCase())
                            }
                            it.show(activity.supportFragmentManager, DIALOG_TAG)
                            activity.toast("Long Clicked")
                        }
                        true
                    }
                }
                setOnClickListener {

                    onClick(movieNames[adapterPosition])
                }
            }
        }

        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val (height: Int, width: Int) = options.run { outHeight to outWidth }
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight: Int = height / 2
                val halfWidth: Int = width / 2

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
    }

    companion object {
        const val KEY_FILE_NAME = "file_name"
        const val DIALOG_TAG = "Dialog"
    }
}
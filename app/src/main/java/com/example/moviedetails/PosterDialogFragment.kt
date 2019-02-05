package com.example.moviedetails

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.R
import kotlinx.android.synthetic.main.fragment_poster_dialog.view.*
import timber.log.Timber

class PosterDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val fileName = arguments?.getString(KEY_FILE_NAME)

        val rootView = inflater.inflate(R.layout.fragment_poster_dialog, container, false)


        Glide.with(this@PosterDialogFragment)
            .load(
                when (fileName) {
                    "civil_war" -> "https://upload.wikimedia.org/wikipedia/en/5/53/Captain_America_Civil_War_poster.jpg"
                    "avengers" -> "https://upload.wikimedia.org/wikipedia/en/f/f9/TheAvengers2012Poster.jpg"
                    else -> resources.getIdentifier(fileName, "drawable", activity?.packageName)
                }
            ).into(rootView.ivDialogImage)



        return rootView
    }

    companion object {
        const val KEY_FILE_NAME = "file_name"
    }
}
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

        Timber.d("onCreateView: $fileName")
        Timber.d("onCreateView: ${resources.getIdentifier(fileName, "drawable", activity?.packageName)}")
        Timber.d("onCreateView: ${activity?.packageName}")

        val rootView = inflater.inflate(R.layout.fragment_poster_dialog, container, false)

        if (fileName.equals("civil_war")) {
            Glide.with(this@PosterDialogFragment)
                .load("https://upload.wikimedia.org/wikipedia/en/5/53/Captain_America_Civil_War_poster.jpg")
                .into(rootView.ivDialogImage)
        }
        else if(fileName.equals("avengers")){
            Glide.with(this@PosterDialogFragment)
                .load("https://upload.wikimedia.org/wikipedia/en/f/f9/TheAvengers2012Poster.jpg")
                .into(rootView.ivDialogImage)
        }
        else {
            Glide.with(this@PosterDialogFragment)
                .load(resources.getIdentifier(fileName, "drawable", activity?.packageName))
                .into(rootView.ivDialogImage)
        }
        /*rootView.ivDialogPoster.setImageResource(
            resources.getIdentifier(fileName, "drawable", activity?.packageName)
        )*/
        return rootView
    }

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {



        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Image")
                .create()
        }
    }*/

    companion object {
        const val KEY_FILE_NAME = "file_name"
    }
}
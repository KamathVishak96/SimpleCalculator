package com.example.moviedetails

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import timber.log.Timber

class MovieDetailsDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        Timber.d("DIALOG")

        val detail = arguments?.run {

            //            reformatString(getString(Movie.KEY_DIALOG) as String)
            getString(MoviesActivity.KEY_DIALOG)

        } ?: "No Movie Viewed Recently"
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Recently Viewed")
                .setMessage(detail + "")
                .create()
        }
    }
}

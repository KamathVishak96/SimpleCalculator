package com.example.moviedetails

import android.os.Bundle
import android.os.Environment
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_poster_dialog.*
import timber.log.Timber
import java.util.concurrent.Executor


class PosterDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.R.layout.fragment_poster_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated: of PosterDialogFragment")

        val fileName = arguments?.getString(KEY_FILE_NAME) ?: ""


        val url = when (fileName) {
            "civil_war" -> "5/53/Captain_America_Civil_War_poster.jpg"
            "avengers" -> "f/f9/TheAvengers2012Poster.jpg"
            "infinity_war" -> "4/4d/Avengers_Infinity_War_poster.jpg"
            "aquaman" -> "3/3a/Aquaman_poster.jpg"
            "age_of_ultron" -> "f/ff/Avengers_Age_of_Ultron_poster.jpg"
            else -> ""
        }

        /*val work = OneTimeWorkRequest.Builder(PosterDownloadWorker::class.java)
            .setInputData(
                Data.Builder()
                    .putString("url", url)
                    .putString(KEY_FILE_NAME, fileName)
                    .build()
            )
            .addTag(KEY_POSTER_DOWNLOADER)
            .build()

        WorkManager.getInstance().beginUniqueWork(
            KEY_POSTER_DOWNLOADER, ExistingWorkPolicy.REPLACE,
            work
        ).enqueue().result
*/
        Glide.with(activity!!)
            .load("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/Posters/$fileName.jpg")
            .apply(RequestOptions().override(360, 720))
            .into(ivDialogImage)

    }

    internal inner class CurrentThreadExecutor : Executor {
        override fun execute(r: Runnable) {
            r.run()
        }
    }

    companion object {
        const val KEY_IMAGE_VIEW = "iv"
        const val KEY_FILE_NAME = "file_name"
        const val KEY_POSTER_DOWNLOADER = "download_poster"
    }
}


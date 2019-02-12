package com.example.moviedetails

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.utils.extensions.toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

class PosterDownloadWorker(val context: Context, val params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {

        while (true) {
            if ((context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                    .activeNetworkInfo?.isConnected == true
            ) {

                /*val url: String = params.inputData.keyValueMap["url"].toString()
                val name: String = params.inputData.keyValueMap[KEY_FILE_NAME].toString()

                (RetrofitFactory.getInstance()?.create(PosterDownloadService::class.java)?.getImage(url))?.apply {
                    request()
                        ?.url()

                    enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            context.toast("Download Failed")
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                saveImage(BitmapFactory.decodeStream(response.body()?.byteStream()), name)
                            }
                        }

                    })

                }*/
                break
            } else {
                /*Thread.sleep(10000)*/
            }
        }

        Timber.i("doWork: Connected")
        return Result.success()
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

    companion object {
        const val KEY_IMAGE_VIEW = "iv"
        const val KEY_FILE_NAME = "file_name"
        const val POSTER_DOWNLOADER = "download_poster"
    }
}






package com.example.retrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
        tvRetrofitResult

         getPost()
        // getComments()
        //createPost()
    }

    private fun createPost() {

        getRetrofitInstance()
            .createPost(Post(10, "Title", "Text"))
            .enqueue(object : Callback<Post> {
                override fun onFailure(call: Call<Post>?, t: Throwable?) {
                    tvRetrofitResult.text = t?.message

                }

                override fun onResponse(call: Call<Post>?, response: Response<Post>?) {
                    if (response != null && !response.isSuccessful) {
                        Timber.d("onResponse: ${response.code()}")
                        tvRetrofitResult.text = "${response.code()}"
                        return
                    }

                    response?.body()?.let {
                        val content = StringBuilder()

                        content.append("Code: ${response.code()}")

                        content.append("ID: ${it.id}\n")

                        content.append("UID: ${it.userId}\n")

                        content.append("Title: ${it.title}\n")

                        content.append("Text: ${it.text}\n\n")

                        tvRetrofitResult.append(content)

                    }
                }

            })



    }

    private fun getComments() {
        getRetrofitInstance()
            .getComments(2)
            .enqueue(object : Callback<List<Comments>> {
                override fun onFailure(call: Call<List<Comments>>?, t: Throwable?) {
                    tvRetrofitResult.text = t?.message
                }

                override fun onResponse(call: Call<List<Comments>>?, response: Response<List<Comments>>?) {
                    if (response != null && !response.isSuccessful) {
                        Timber.d("onResponse: ${response.code()}")
                        tvRetrofitResult.text = "${response.code()}"
                        return
                    }
                    response?.body()?.forEach {
                        val content = StringBuilder()
                        Timber.d("onResponse: ${it.id}")
                        content.append("ID: ${it.id}\n")
                        content.append("PostID: ${it.postId}\n")
                        content.append("Name: ${it.name}\n")
                        content.append("email: ${it.email}\n")
                        content.append("Text: ${it.text}\n\n")

                        tvRetrofitResult.append(content)

                    }
                }

            })
    }

    private fun getPost() {
        getRetrofitInstance()
            .getPosts(4, "id", "desc")
            .enqueue(object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
                    tvRetrofitResult.text = t?.message
                }

                override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {
                    if (response != null && !response.isSuccessful) {
                        Timber.d("onResponse: ${response.code()}")
                        tvRetrofitResult.text = "${response.code()}"
                        return
                    }

                    response?.body()?.forEach {
                        val content = StringBuilder()
                        Timber.d("onResponse: ${it.id}")
                        content.append("ID: ${it.id}\n")
                        Timber.d("onResponse: ${it.userId}")
                        content.append("UID: ${it.userId}\n")
                        Timber.d("onResponse: ${it.title}")
                        content.append("Title: ${it.title}\n")
                        Timber.d("onResponse: ${it.text}")
                        content.append("Text: ${it.text}\n\n")

                        tvRetrofitResult.append(content)

                    }
                }

            })
    }

    private fun getRetrofitInstance() = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JsonDownloadService::class.java)


}

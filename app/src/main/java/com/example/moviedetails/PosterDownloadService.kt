package com.example.moviedetails

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PosterDownloadService {
    @GET
    fun getImage(@Url url: String): Call<ResponseBody>
}
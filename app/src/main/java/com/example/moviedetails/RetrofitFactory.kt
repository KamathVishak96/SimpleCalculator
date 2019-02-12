package com.example.moviedetails

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitFactory {


    companion object {
        private var retrofit: Retrofit? = null

        fun getInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl("https://upload.wikimedia.org/wikipedia/en/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}
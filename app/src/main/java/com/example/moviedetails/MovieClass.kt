package com.example.moviedetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(
    val title: String,
    val actor: String,
    val year: Int,
    val rating: Double
): Parcelable {

    override fun toString(): String {
        return """Title: $title
                |Actor: $actor
                |Year: $year
                |Rating: $rating
            """.trimMargin()
    }

    companion object {
        const val KEY = "Movie"
    }
}
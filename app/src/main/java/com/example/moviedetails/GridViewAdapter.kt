package com.example.moviedetails

import android.content.Context
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.R
import kotlinx.android.synthetic.main.cardview_movies_list.view.*

class GridViewAdapter(private val context: Context) : BaseAdapter() {

    private var movieNames = emptyList<Movie>()

    override fun getCount(): Int = movieNames.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0L

    fun setMovieDetails(movieNameList: List<Movie>){
        movieNames = movieNameList
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = if (convertView == null) {
            LayoutInflater.from(context).inflate(R.layout.cardview_movies_list, null, false)
        } else {
            convertView
        }
        view.tvCVMovieTitle.text = movieNames[position].title
        view.tvCVMovieActor.text = movieNames[position].actor
        view.tvCVMovieYear.text = movieNames[position].year.toString()
        view.tvCVMovieRating.text = movieNames[position].rating.toString()

        return view
    }
}
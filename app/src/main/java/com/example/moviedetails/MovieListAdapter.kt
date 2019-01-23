package com.example.moviedetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import kotlinx.android.synthetic.main.rv_movie_list_item.view.*

class MovieListAdapter(val onClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private var movieNames = emptyList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_movie_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieNames[position])
    }

    override fun getItemCount() = movieNames.size

    fun setMovieList(movieNameList: List<Movie>) {
        movieNames = movieNameList
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(movie: Movie) {
            with(itemView) {
                tvMovieName.text = movie.title
                setOnClickListener {

                    onClick(movieNames[adapterPosition])
                }
            }
        }
    }
}
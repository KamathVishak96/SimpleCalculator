package com.example.moviedetails

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.R
import com.example.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.rv_movie_list_item.*


class MovieListFragment : Fragment() {

    private lateinit var eventListener: EventListener
    private val moviesList by lazy {
        listOf(
            Movie("Civil War", "Chris Evans", 2016, 7.8),
            Movie("Avengers", "RDJ", 2012, 8.1),
            Movie("Age of Ultron", "Chris Evans", 2015, 7.2),
            Movie("Infinity War", "RDJ", 2018, 8.9),
            Movie("Aquaman", "Jason Momoa", 2018, 8.3)
        )
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is EventListener) {
            eventListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMovies?.run {
            layoutManager = LinearLayoutManager(activity)

            if (activity != null) {
                val movieDetailsAdapter = MovieListAdapter(activity!!) {
                    eventListener.onMovieItemClick(it)
                }
                movieDetailsAdapter.setMovieList(moviesList)
                adapter = movieDetailsAdapter
            }
        }

    }

    interface EventListener {
        fun onMovieItemClick(movie: Movie)
    }

    companion object {
        val context = this
        const val KEY = "MovieListFragment"
    }
}

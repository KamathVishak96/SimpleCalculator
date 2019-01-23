package com.example.moviedetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import com.example.R
import kotlinx.android.synthetic.main.activity_movies_card_view.*

class MoviesCardViewActivity : AppCompatActivity() {

    private val moviesList by lazy {
        listOf(
            Movie("Civil War", "Chris Evans", 2016, 7.8),
            Movie("Avengers", "RDJ", 2012, 8.1),
            Movie("Age of Ultron", "Chris Evans", 2015, 7.2),
            Movie("Infinity War", "RDJ", 2018, 8.9),
            Movie("Aquaman", "Jason Momoa", 2018, 8.3)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_card_view)
        gvMoviesList.visibility = View.GONE
        rvCardViewMovies.visibility = View.VISIBLE

        setLayout(btnChangeLayoutMovies)

        btnChangeLayoutMovies.setOnClickListener {
            btnChangeLayoutMovies.let { btn ->
                if (btn.text == getString(R.string.card_view)) {
                    gvMoviesList.visibility = View.GONE

                    setLayout(btn)

                } else {
                    btn.text = getString(R.string.card_view)
                    rvCardViewMovies.visibility = View.GONE
                    gvMoviesList.apply {
                        visibility = View.VISIBLE
                        val movieListAdapter = GridViewAdapter(this@MoviesCardViewActivity)
                        movieListAdapter.setMovieDetails(moviesList)
                        adapter = movieListAdapter
                    }
                }

            }
        }

    }

    private fun setLayout(btn: Button) {
        rvCardViewMovies.apply {
            visibility = View.VISIBLE
            setHasFixedSize(true)
            layoutManager =
                    LinearLayoutManager(this@MoviesCardViewActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    this@MoviesCardViewActivity,
                    DividerItemDecoration.HORIZONTAL
                )
            )
            btn.text = getString(R.string.grid_view)
            val movieListAdapter = MovieListCVAdapter()
            movieListAdapter.setMovieList(moviesList)
            adapter = movieListAdapter
        }
    }
}




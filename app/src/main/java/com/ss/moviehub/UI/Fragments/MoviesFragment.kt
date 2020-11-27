package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.MovieAdapter
import com.ss.moviehub.Models.Result
import com.ss.moviehub.UI.MainActivity
import com.ss.moviehub.UI.ViewModel.MovieViewModel

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    // View Model
    private lateinit var viewModel: MovieViewModel

    // Recycler View
    private lateinit var popularRecyclerView: RecyclerView
    private lateinit var topRatedRecyclerView: RecyclerView
    private lateinit var upcomingRecyclerView: RecyclerView

    // Adapter
    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var topRatedMovieAdapter: MovieAdapter
    private lateinit var upcomingMovieAdapter: MovieAdapter

    // Movies Has Poster
    private lateinit var popularMoviesHasPoster: MutableList<Result>
    private lateinit var topRatedMoviesHasPoster: MutableList<Result>
    private lateinit var upcomingMoviesHasPoster: MutableList<Result>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        getMovies()
    }

    private fun init() {
        // Adapter
        popularMovieAdapter = MovieAdapter("MovieFragment")
        topRatedMovieAdapter = MovieAdapter("MovieFragment")
        upcomingMovieAdapter = MovieAdapter("MovieFragment")

        // Recycler View
        popularRecyclerView = requireView().findViewById(R.id.popular_movies)
        topRatedRecyclerView = requireView().findViewById(R.id.top_rated_movies)
        upcomingRecyclerView = requireView().findViewById(R.id.upcoming_movies)

        // View Model
        viewModel = (activity as MainActivity).viewModel

        // Movies Has Poster
        popularMoviesHasPoster = mutableListOf()
        topRatedMoviesHasPoster = mutableListOf()
        upcomingMoviesHasPoster = mutableListOf()
    }

    private fun getMovies() {
        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner, {
            popularRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            for (movie in it) {
                if (movie.poster_path != null) {
                    popularMoviesHasPoster.add(movie)
                }
            }
            popularMovieAdapter.differ.submitList(popularMoviesHasPoster)
            popularRecyclerView.adapter = popularMovieAdapter
        })

        viewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner, {
            topRatedRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            for (movie in it) {
                if (movie.poster_path != null) {
                    topRatedMoviesHasPoster.add(movie)
                }
            }
            topRatedMovieAdapter.differ.submitList(topRatedMoviesHasPoster)
            topRatedRecyclerView.adapter = topRatedMovieAdapter
        })

        viewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner, {
            upcomingRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            for (movie in it) {
                if (movie.poster_path != null) {
                    upcomingMoviesHasPoster.add(movie)
                }
            }
            upcomingMovieAdapter.differ.submitList(upcomingMoviesHasPoster)
            upcomingRecyclerView.adapter = upcomingMovieAdapter
        })
    }
}
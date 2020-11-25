package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.MovieAdapter
import com.ss.moviehub.ViewModel.MovieViewModel

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
        popularRecyclerView = view?.findViewById(R.id.popular_movies)!!
        topRatedRecyclerView = view?.findViewById(R.id.top_rated_movies)!!
        upcomingRecyclerView = view?.findViewById(R.id.upcoming_movies)!!

        // View Model
        viewModel = MovieViewModel()
    }

    private fun getMovies() {
        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner, {
            popularRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            popularMovieAdapter.differ.submitList(it)
            popularRecyclerView.adapter = popularMovieAdapter
        })

        viewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner, {
            topRatedRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            topRatedMovieAdapter.differ.submitList(it)
            topRatedRecyclerView.adapter = topRatedMovieAdapter
        })

        viewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner, {
            upcomingRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            upcomingMovieAdapter.differ.submitList(it)
            upcomingRecyclerView.adapter = upcomingMovieAdapter
        })
    }
}
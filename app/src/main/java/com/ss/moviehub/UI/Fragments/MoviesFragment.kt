package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.RecyclerAdapter
import com.ss.moviehub.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private lateinit var movieItemLiveData: MovieViewModel
    private lateinit var popularRecycler: RecyclerView
    private lateinit var topRatedRecycler: RecyclerView
    private lateinit var upcomingRecycler: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        getMovies()
    }

    private fun init() {
        movieItemLiveData = MovieViewModel()
        popularRecycler = view?.findViewById(R.id.popular_movies)!!
        topRatedRecycler = view?.findViewById(R.id.top_rated_movies)!!
        upcomingRecycler = view?.findViewById(R.id.upcoming_movies)!!
    }

    private fun getMovies() {
        movieItemLiveData.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            popular_movies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            popularRecycler.adapter = RecyclerAdapter(it, "MovieFragment")
        })

        movieItemLiveData.topRatedMoviesLiveData.observe(viewLifecycleOwner, Observer {
            top_rated_movies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            topRatedRecycler.adapter = RecyclerAdapter(it, "MovieFragment")
        })

        movieItemLiveData.upcomingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            upcoming_movies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            upcomingRecycler.adapter = RecyclerAdapter(it, "MovieFragment")
        })
    }
}
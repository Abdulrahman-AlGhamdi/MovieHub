package com.ss.moviehub.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.RecyclerAdapter
import com.ss.moviehub.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private lateinit var movieItemLiveData: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        getMovies()
    }

    private fun init() {
        movieItemLiveData = MovieViewModel()
    }

    private fun getMovies() {
        movieItemLiveData.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            popular_movies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            popular_movies.adapter = RecyclerAdapter(it, "MovieFragment")
        })

        movieItemLiveData.topRatedMoviesLiveData.observe(viewLifecycleOwner, Observer {
            top_rated_movies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            top_rated_movies.adapter = RecyclerAdapter(it, "MovieFragment")
        })

        movieItemLiveData.upcomingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            upcoming_movies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            upcoming_movies.adapter = RecyclerAdapter(it, "MovieFragment")
        })
    }
}
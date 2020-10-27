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

class MoviesFragment : Fragment() {

    private lateinit var movieView: View
    private lateinit var popularMovies: RecyclerView
    private lateinit var topRatedMovies: RecyclerView
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var movieItemLiveData: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        movieView = inflater.inflate(R.layout.fragment_movies, container, false)

        init()
        getMovies()

        return movieView
    }

    private fun init() {
        movieItemLiveData = MovieViewModel()
        popularMovies = movieView.findViewById(R.id.popular_movies)
        topRatedMovies = movieView.findViewById(R.id.top_rated_movies)
        upcomingMovies = movieView.findViewById(R.id.upcoming_movies)
    }

    private fun getMovies() {
        movieItemLiveData.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
            popularMovies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            popularMovies.adapter = RecyclerAdapter(it)
        })

        movieItemLiveData.topRatedMoviesLiveData.observe(viewLifecycleOwner, Observer {
            topRatedMovies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            topRatedMovies.adapter = RecyclerAdapter(it)
        })

        movieItemLiveData.upcomingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            upcomingMovies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            upcomingMovies.adapter = RecyclerAdapter(it)
        })
    }
}
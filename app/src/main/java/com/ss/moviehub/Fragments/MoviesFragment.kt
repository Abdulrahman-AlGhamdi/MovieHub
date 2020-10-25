package com.ss.moviehub.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesFragment : Fragment() {

    private var COUNT = 0
    private lateinit var movieView: View
    private lateinit var popularMovies: RecyclerView
    private lateinit var topRatedMovies: RecyclerView
    private lateinit var upcomingMovies: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        movieView = inflater.inflate(R.layout.fragment_movies, container, false)

        init()
        getMovies()

        return movieView
    }

    private fun init(){
        popularMovies = movieView.findViewById(R.id.popular_movies)
        topRatedMovies = movieView.findViewById(R.id.top_rated_movies)
        upcomingMovies = movieView.findViewById(R.id.upcoming_movies)
    }

    private fun getMovies(){
        if (COUNT == 0) {
            GlobalScope.launch {
                Repository().getPopularMovie()
                Repository().getTopRatedMovie()
                Repository().getUpcomingMovie()

                withContext(Dispatchers.Main) {
                    Repository().setupRecyclerView(popularMovies)
                    Repository().setupRecyclerView(topRatedMovies)
                    Repository().setupRecyclerView(upcomingMovies)
                }
            }
            COUNT++
        }

        Repository().setupRecyclerView(popularMovies)
        Repository().setupRecyclerView(topRatedMovies)
        Repository().setupRecyclerView(upcomingMovies)
    }
}
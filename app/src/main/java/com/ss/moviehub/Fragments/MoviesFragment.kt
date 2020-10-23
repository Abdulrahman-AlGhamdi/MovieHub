package com.ss.moviehub.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.API.Repository
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class MoviesFragment : Fragment() {

    private var COUNT = 0
    private lateinit var popularMovies: RecyclerView
    private lateinit var topRatedMovies: RecyclerView
    private lateinit var upcomingMovies: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        popularMovies = view.findViewById(R.id.popular_movies)
        topRatedMovies = view.findViewById(R.id.top_rated_movies)
        upcomingMovies = view.findViewById(R.id.upcoming_movies)

        if (COUNT == 0) {
            movieRequest()
            topRatedMoviesRequest()
            upcomingMoviesRequest()
            COUNT++
        }

        Repository().setupRecyclerView(popularMovies)
        Repository().setupRecyclerView(topRatedMovies)
        Repository().setupRecyclerView(upcomingMovies)

        return view
    }

    private fun movieRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            val popularResponse = api.getPopularMovie().awaitResponse()

            try {
                if (popularResponse.isSuccessful) {
                    for (popularMovie in popularResponse.body()?.results!!) {
                        Repository().addToList(
                            "popular",
                            popularMovie.poster_path,
                            popularMovie.title,
                            popularMovie.backdrop_path,
                            popularMovie.release_date,
                            popularMovie.overview,
                            popularMovie.vote_average
                        )
                    }

                    withContext(Dispatchers.Main) {
                        Repository().setupRecyclerView(popularMovies)
                    }
                }

            } catch (e: Exception) {
                Log.d("MovieResult", e.toString())
            }
        }
    }

    private fun topRatedMoviesRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            val topRatedResponse = api.getTopRatedMovie().awaitResponse()

            try {
                if (topRatedResponse.isSuccessful) {
                    for (popularMovie in topRatedResponse.body()?.results!!) {
                        Repository().addToList(
                            "topRated",
                            popularMovie.poster_path,
                            popularMovie.title,
                            popularMovie.backdrop_path,
                            popularMovie.release_date,
                            popularMovie.overview,
                            popularMovie.vote_average
                        )
                    }

                    withContext(Dispatchers.Main) {
                        Repository().setupRecyclerView(topRatedMovies)
                    }
                }
            } catch (e: Exception) {
                Log.d("MovieResult", e.toString())
            }
        }
    }

    private fun upcomingMoviesRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            val upcomingResponse = api.getUpcomingMovie().awaitResponse()

            try {
                if (upcomingResponse.isSuccessful) {
                    for (upcoming in upcomingResponse.body()?.results!!) {
                        Repository().addToList(
                            "upcoming",
                            upcoming.poster_path,
                            upcoming.title,
                            upcoming.backdrop_path,
                            upcoming.release_date,
                            upcoming.overview,
                            upcoming.vote_average
                        )
                    }

                    withContext(Dispatchers.Main) {
                        Repository().setupRecyclerView(upcomingMovies)
                    }
                }
            } catch (e: Exception) {
                Log.d("MovieResult", e.toString())
            }
        }
    }
}
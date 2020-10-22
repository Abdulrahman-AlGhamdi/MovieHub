package com.ss.moviehub.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ss.moviehub.*
import com.ss.moviehub.API.Repository
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        movieRequest()

        return view
    }

    private fun movieRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val popularResponse = api.getPopularMovie().awaitResponse()
                val topRatedResponse = api.getTopRatedMovie().awaitResponse()

                if (popularResponse.isSuccessful) {
                    for (popularMovie in popularResponse.body()?.results!!) {
                        Repository().addToList(
                            popularMovie.poster_path,
                            popularMovie.title,
                            popularMovie.backdrop_path,
                            popularMovie.release_date,
                            popularMovie.overview,
                            popularMovie.vote_average
                        )
                    }

                    withContext(Dispatchers.Main) {
                        Repository().setupRecyclerView(popular_movies)
                    }
                }

                if (topRatedResponse.isSuccessful) {
                    for (topRated in topRatedResponse.body()?.results!!) {
                        Repository().addToTopRatedList(
                            topRated.poster_path,
                            topRated.title,
                            topRated.backdrop_path,
                            topRated.release_date,
                            topRated.overview,
                            topRated.vote_average
                        )
                    }

                    withContext(Dispatchers.Main) {
                        Repository().setupRecyclerView(top_rated_movies)
                    }
                }

            } catch (e: Exception) {
                Log.d("MovieResult", e.toString())
            }
        }
    }
}
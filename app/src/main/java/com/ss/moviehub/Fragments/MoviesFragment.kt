package com.ss.moviehub.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Adapters.RecyclerAdapter
import com.ss.moviehub.R
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MoviesFragment : Fragment() {

    private var posterList = mutableListOf<String>()
    private var titleList = mutableListOf<String>()
    private var backdropList = mutableListOf<String>()
    private var releaseDateList = mutableListOf<String>()
    private var overviewList = mutableListOf<String>()
    private var voteAverageList = mutableListOf<Double>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        popularMovieRequest()

        return view
    }

    private fun popularMovieRequest() {
        val api = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getPopularMovie().awaitResponse()

                if (response.isSuccessful) {
                    for (movie in response.body()?.results!!) {
                        addToList(
                            movie.poster_path,
                            movie.title,
                            movie.backdrop_path,
                            movie.release_date,
                            movie.overview,
                            movie.vote_average
                        )
                    }
                    withContext(Dispatchers.Main) {
                        setupRecyclerView()
                    }
                }

            } catch (e: Exception) {
                Log.d("MovieResult", e.toString())
            }
        }
    }

    private fun addToList(
        poster: String,
        title: String,
        backdrop: String,
        releaseDate: String,
        overview: String,
        voteAverage: Double
    ) {
        posterList.add(poster)
        titleList.add(title)
        backdropList.add(backdrop)
        releaseDateList.add(releaseDate)
        overviewList.add(overview)
        voteAverageList.add(voteAverage)
    }

    private fun setupRecyclerView() {
        popular_movies.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        popular_movies.adapter = RecyclerAdapter(
            posterList,
            titleList,
            backdropList,
            releaseDateList,
            overviewList,
            voteAverageList
        )
    }
}
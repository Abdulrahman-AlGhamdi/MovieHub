package com.ss.moviehub.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.ss.moviehub.*
import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Adapters.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        posterList.clear()
        titleList.clear()
        backdropList.clear()
        releaseDateList.clear()
        overviewList.clear()
        voteAverageList.clear()

        val searchMovie = view.findViewById<SearchView>(R.id.search_movie)

        searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchedMovieRequest(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                posterList.clear()
                titleList.clear()
                backdropList.clear()
                releaseDateList.clear()
                overviewList.clear()
                voteAverageList.clear()
                result.text = "Search Result For: $newText"
                searchedMovieRequest(newText.toString())
                return false
            }
        })
        return view
    }

    private fun searchedMovieRequest(search: String) {
        val api = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response =
                    api.getSearchedMovie("c549b0b6a42c2b56589e9be69b41897c", search).awaitResponse()

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
        result_movie.layoutManager = GridLayoutManager(context, 3)
        result_movie.adapter = RecyclerAdapter(
            posterList,
            titleList,
            backdropList,
            releaseDateList,
            overviewList,
            voteAverageList
        )
    }
}
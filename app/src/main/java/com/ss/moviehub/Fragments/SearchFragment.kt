package com.ss.moviehub.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.ss.moviehub.*
import com.ss.moviehub.API.Repository
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class SearchFragment : Fragment() {

    private lateinit var searchMovie: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchMovie = view.findViewById(R.id.search_movie)

        searchMovie()

        return view
    }

    private fun searchMovie(){
        searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchPosterList.clear()
                searchTitleLists.clear()
                searchBackdropLists.clear()
                searchReleaseDateLists.clear()
                searchOverviewLists.clear()
                searchVoteAverageLists.clear()
                result.text = "Search Result For: $query"
                searchMovieRequest(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun searchMovieRequest(search: String) {

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
                        Repository().setupRecyclerView(result_movie)
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
        searchPosterList.add(poster)
        searchTitleLists.add(title)
        searchBackdropLists.add(backdrop)
        searchReleaseDateLists.add(releaseDate)
        searchOverviewLists.add(overview)
        searchVoteAverageLists.add(voteAverage)
    }
}
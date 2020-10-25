package com.ss.moviehub.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Repository.Repository
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    private lateinit var searchView: View
    private lateinit var searchMovie: SearchView
    private lateinit var searchResultMovies: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        searchView = inflater.inflate(R.layout.fragment_search, container, false)

        init()
        searchMovie()

        return searchView
    }

    private fun init() {
        searchMovie = searchView.findViewById(R.id.search_movie)
        searchResultMovies = searchView.findViewById(R.id.result_movie)
    }

    private fun searchMovie() {
        searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchPosterList.clear()
                searchTitleLists.clear()
                searchBackdropLists.clear()
                searchReleaseDateLists.clear()
                searchOverviewLists.clear()
                searchVoteAverageLists.clear()
                result.text = "Search Result For: $query"
                GlobalScope.launch {
                    Repository().getSearchedMovie(query.toString())

                    withContext(Dispatchers.Main) {
                        Repository().setupRecyclerView(searchResultMovies)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        Repository().setupRecyclerView(searchResultMovies)
    }
}
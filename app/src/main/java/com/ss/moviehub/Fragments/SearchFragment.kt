package com.ss.moviehub.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.RecyclerAdapter
import com.ss.moviehub.Repository.Repository
import com.ss.moviehub.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var searchView: View
    private lateinit var searchMovie: SearchView
    private lateinit var searchResultMovies: RecyclerView
    private lateinit var movieItemLiveData: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        searchView = inflater.inflate(R.layout.fragment_search, container, false)

        init()
        searchMovie()

        return searchView
    }

    private fun init() {
        movieItemLiveData = MovieViewModel()
        searchMovie = searchView.findViewById(R.id.search_movie)
        searchResultMovies = searchView.findViewById(R.id.result_movie)
    }

    private fun searchMovie() {
        searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                result.text = "Search Result For: $query"
                movieItemLiveData.searchedMoviesLiveData =
                    Repository().getSearchedMovie(query.toString())
                movieItemLiveData.searchedMoviesLiveData.observe(viewLifecycleOwner, Observer {
                    searchResultMovies.layoutManager = GridLayoutManager(context, 3)
                    searchResultMovies.adapter = RecyclerAdapter(it)
                })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
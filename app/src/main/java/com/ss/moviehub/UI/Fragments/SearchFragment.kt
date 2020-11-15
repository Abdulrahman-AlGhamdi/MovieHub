package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.RecyclerAdapter
import com.ss.moviehub.Repository.Repository
import com.ss.moviehub.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var movieItemLiveData: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        searchMovie()
    }

    private fun init() {
        movieItemLiveData = MovieViewModel()
    }

    private fun searchMovie() {
        search_movie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                result.text = "Search Result For: $query"
                movieItemLiveData.searchedMoviesLiveData =
                    Repository().getSearchedMovie(query.toString())
                movieItemLiveData.searchedMoviesLiveData.observe(viewLifecycleOwner, Observer {
                    result_movie.layoutManager = GridLayoutManager(context, 3)
                    result_movie.adapter = RecyclerAdapter(it , "SearchFragment")
                })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
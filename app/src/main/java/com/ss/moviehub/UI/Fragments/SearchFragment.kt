package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.MovieAdapter
import com.ss.moviehub.Repository.MovieRepository
import com.ss.moviehub.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var search: SearchView
    private lateinit var searchAdapter: MovieAdapter
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var viewModel: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        searchMovie()
    }

    private fun init() {
        viewModel = MovieViewModel()
        search = view?.findViewById(R.id.search_movie)!!
        searchAdapter = MovieAdapter("SearchFragment")
        searchRecyclerView = view?.findViewById(R.id.searched_movie)!!
    }

    private fun searchMovie() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchResultFor.text = "Search Result For: $query"
                viewModel.getSearchMovie(query.toString())
                viewModel.searchedMoviesLiveData.observe(viewLifecycleOwner, {
                    searchRecyclerView.layoutManager = GridLayoutManager(context, 3)
                    searchAdapter.differ.submitList(it)
                    searchRecyclerView.adapter = searchAdapter
                })
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.MovieAdapter
import com.ss.moviehub.Models.Result
import com.ss.moviehub.UI.MainActivity
import com.ss.moviehub.UI.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var moviesPoster: MutableList<Result>
    private lateinit var search: SearchView
    private lateinit var searchedMovie: String
    private lateinit var searchAdapter: MovieAdapter
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var viewModel: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(savedInstanceState)
        searchMovie()
    }

    private fun init(savedInstanceState: Bundle?) {
        moviesPoster = mutableListOf()
        viewModel = (activity as MainActivity).viewModel
        search = requireView().findViewById(R.id.search_movie)
        searchAdapter = MovieAdapter("SearchFragment")
        searchRecyclerView = requireView().findViewById(R.id.searched_movie)
        if (savedInstanceState != null)
            searchedMovie = savedInstanceState.getString("Searched Movie")!!
    }

    private fun searchMovie() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchedMovie = newText.toString()
                searchResultFor.text = "Search Result For: $searchedMovie"
                viewModel.getSearchMovie(searchedMovie)
                viewModel.searchedMoviesLiveData.observe(viewLifecycleOwner, {
                    searchRecyclerView.layoutManager = GridLayoutManager(context, 3)
                    moviesPoster.clear()
                    for (movie in it){
                        if (movie.poster_path != null){
                            moviesPoster.add(movie)
                        }
                    }
                    if (searchedMovie.isBlank())
                        moviesPoster.clear()
                    searchAdapter.differ.submitList(moviesPoster)
                    searchRecyclerView.adapter = searchAdapter
                })
                return false
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Searched Movie", searchedMovie)
    }
}
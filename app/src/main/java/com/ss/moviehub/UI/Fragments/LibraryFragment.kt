package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.Adapters.MovieAdapter
import com.ss.moviehub.Models.Result
import com.ss.moviehub.R
import com.ss.moviehub.UI.MainActivity
import com.ss.moviehub.UI.ViewModel.MovieViewModel

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private lateinit var viewModel: MovieViewModel
    private lateinit var libraryAdapter: MovieAdapter
    private lateinit var libraryList: MutableList<Result>
    private lateinit var libraryRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindLibraryMovies()
    }

    private fun initViews() {
        viewModel = (activity as MainActivity).viewModel
        libraryList = mutableListOf()
        libraryAdapter = MovieAdapter("LibraryFragment")
        libraryRecyclerView = view?.findViewById(R.id.library_movies)!!
        libraryRecyclerView.layoutManager = GridLayoutManager(context, 3)
    }

    private fun bindLibraryMovies() {
        viewModel.getLibraryMovies().observe(viewLifecycleOwner, {
            libraryAdapter.differ.submitList(it)
            libraryRecyclerView.adapter = libraryAdapter
        })
    }
}
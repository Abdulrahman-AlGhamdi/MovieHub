package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.Adapters.MovieAdapter
import com.ss.moviehub.Models.Result
import com.ss.moviehub.R

var libraryListHolder: ArrayList<Result> = ArrayList()

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private lateinit var libraryAdapter: MovieAdapter
    private lateinit var libraryList: MutableList<Result>
    private lateinit var libraryRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindLibraryMovies()
    }

    private fun initViews() {
        libraryList = mutableListOf()
        libraryAdapter = MovieAdapter("LibraryFragment")
        libraryRecyclerView = view?.findViewById(R.id.library_movies)!!
        libraryRecyclerView.layoutManager = GridLayoutManager(context, 3)
    }

    private fun bindLibraryMovies() {
        libraryList.clear()
        libraryListHolder.forEach {
            libraryList.add(it)
        }
        libraryAdapter.differ.submitList(libraryList)
        libraryRecyclerView.adapter = libraryAdapter
    }
}
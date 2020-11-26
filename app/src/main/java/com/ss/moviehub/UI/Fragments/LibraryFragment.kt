package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ss.moviehub.Adapters.LibraryAdapter
import com.ss.moviehub.Adapters.MovieAdapter
import com.ss.moviehub.Models.Result
import com.ss.moviehub.R
import com.ss.moviehub.UI.MainActivity
import com.ss.moviehub.UI.ViewModel.MovieViewModel

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private lateinit var viewModel: MovieViewModel
    private lateinit var libraryAdapter: LibraryAdapter
    private lateinit var libraryList: MutableList<Result>
    private lateinit var libraryRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindLibraryMovies()
        deleteFromLibrary()
    }

    private fun initViews() {
        viewModel = (activity as MainActivity).viewModel
        libraryList = mutableListOf()
        libraryAdapter = LibraryAdapter()
        libraryRecyclerView = view?.findViewById(R.id.library_movies)!!
        libraryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun bindLibraryMovies() {
        viewModel.getLibraryMovies().observe(viewLifecycleOwner, {
            libraryAdapter.differ.submitList(it)
            libraryRecyclerView.adapter = libraryAdapter
        })
    }

    private fun deleteFromLibrary() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val result = libraryAdapter.differ.currentList[position]
                viewModel.deleteMovieFromLibrary(result)
                result.added = false
                Snackbar.make(requireView(), "Movie Successfully Deleted", Snackbar.LENGTH_SHORT)
                    .setAction("Undo") {
                        viewModel.addMovieToLibrary(result)
                        result.added = true
                    }
                    .show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(libraryRecyclerView)
    }
}
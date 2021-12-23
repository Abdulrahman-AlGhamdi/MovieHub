package com.ss.moviehub.ui.library

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ss.moviehub.R
import com.ss.moviehub.databinding.FragmentLibraryBinding
import com.ss.moviehub.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LibraryFragment : Fragment(R.layout.fragment_library) {

    private val binding by viewBinding(FragmentLibraryBinding::bind)
    private val viewModel: LibraryViewModel by viewModels()
    private val adapter = LibraryAdapter()
    private lateinit var libraryJob: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLibraryMovies()
        deleteFromLibrary()
    }

    private fun showLibraryMovies() {
        libraryJob = lifecycleScope.launchWhenCreated {
            viewModel.getLibraryMovies().collect {
                when (it) {
                    LibraryViewModel.LibraryMoviesState.EmptyList -> {
                        binding.libraryHeader.visibility = View.GONE
                        binding.libraryList.visibility = View.GONE
                        binding.empty.visibility = View.VISIBLE
                        binding.emptyButton.setOnClickListener {
                            val directions = LibraryFragmentDirections
                            val action = directions.actionLibraryFragmentToMoviesFragment()
                            findNavController().navigate(action)
                        }
                    }
                    is LibraryViewModel.LibraryMoviesState.LibraryList -> {
                        setHasOptionsMenu(true)
                        binding.empty.visibility = View.GONE
                        binding.libraryHeader.visibility = View.VISIBLE
                        binding.libraryList.visibility = View.VISIBLE
                        adapter.differ.submitList(it.libraryList)
                        binding.libraryList.adapter = adapter
                    }
                }
            }
        }
    }

    private fun deleteFromLibrary() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val result = adapter.differ.currentList[viewHolder.adapterPosition]
                viewModel.deleteMovieFromLibrary(result)
                result.added = false
                Snackbar.make(requireView(), getString(R.string.successfully_deleted), Snackbar.LENGTH_SHORT).apply {
                    this.setAction(getString(R.string.undo)) {
                        viewModel.addMovieToLibrary(result)
                        result.added = true
                    }
                    this.setAnchorView(R.id.navigation_bar)
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.libraryList)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.library_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_menu)
            AlertDialog.Builder(requireActivity()).apply {
                this.setCancelable(false)
                this.setNegativeButton(getString(R.string.cancel), null)
                this.setPositiveButton(getString(R.string.ok)) { _, _ ->
                    viewModel.deleteAllMovies()
                }
                this.create().apply {
                    this.setTitle(getString(R.string.delete_all_movies))
                    this.show()
                }
            }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        libraryJob.cancel()
    }
}
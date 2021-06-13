package com.ss.moviehub.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ss.moviehub.R
import com.ss.moviehub.databinding.FragmentSearchBinding
import com.ss.moviehub.repository.MoviesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchJob: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        init()
        searchMovie()

        return binding.root
    }

    private fun init() {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val lastSearch = preference.getString("Searched Movie", "") ?: ""
        binding.searchResultFor.text = getString(R.string.search_result_for, lastSearch)
        showSearchedMovies(lastSearch)
    }

    private fun searchMovie() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
                    this.putString("Searched Movie", query ?: "")
                    this.apply()
                }
                binding.searchResultFor.text = getString(R.string.search_result_for, query ?: "")
                showSearchedMovies(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showSearchedMovies(query: String) {
        searchJob = lifecycleScope.launchWhenCreated {
            viewModel.getSearchMovies(query, 1).collect { status ->
                when (status) {
                    is MoviesRepository.MoviesStatus.MoviesFailed ->
                        Snackbar.make(requireView(), status.message, Snackbar.LENGTH_SHORT).show()
                    is MoviesRepository.MoviesStatus.MoviesSuccessful -> {
                        binding.searchList.layoutManager = GridLayoutManager(context, 2)
                        binding.searchList.adapter = SearchAdapter(status.movieList)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::searchJob.isInitialized) searchJob.cancel()
        _binding = null
    }
}
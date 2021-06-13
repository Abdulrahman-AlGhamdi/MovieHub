package com.ss.moviehub.ui.search

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
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
import com.ss.moviehub.ui.search.SearchFragment.ViewState.NoInternet
import com.ss.moviehub.ui.search.SearchFragment.ViewState.WithInternet
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
        val preference = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val lastSearch = preference.getString("Searched Movie", "") ?: ""
        binding.searchResultFor.text = getString(R.string.search_result_for, lastSearch)
        val manager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
            if (capabilities != null) customView(WithInternet(lastSearch)) else customView(NoInternet)
        } else if (manager.activeNetworkInfo != null)  customView(WithInternet(lastSearch)) else customView(NoInternet)
    }

    private fun searchMovie() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val search = query ?: ""
                PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
                    this.putString("Searched Movie", search)
                    this.apply()
                }
                binding.searchResultFor.text = getString(R.string.search_result_for, query ?: "")
                val manager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
                    if (capabilities != null) customView(WithInternet(search)) else customView(NoInternet)
                } else if (manager.activeNetworkInfo != null)  customView(WithInternet(search)) else customView(NoInternet)
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
                        if (status.message.isNotEmpty())
                        Snackbar.make(requireView(), status.message, Snackbar.LENGTH_SHORT).show()
                    is MoviesRepository.MoviesStatus.MoviesSuccessful -> {
                        binding.searchList.layoutManager = GridLayoutManager(requireContext(), 2)
                        binding.searchList.adapter = SearchAdapter(status.movieList)
                    }
                }
            }
        }
    }

    private fun customView(state: ViewState) {
        when (state) {
            is NoInternet -> {
                binding.search.visibility = View.GONE
                binding.searchList.visibility = View.GONE
                binding.searchResultFor.visibility = View.GONE
                binding.noNetwork.visibility = View.VISIBLE
                binding.noNetwork.setOnClickListener { init() }
            }
            is WithInternet -> {
                binding.noNetwork.visibility = View.GONE
                binding.search.visibility = View.VISIBLE
                binding.searchList.visibility = View.VISIBLE
                binding.searchResultFor.visibility = View.VISIBLE
                showSearchedMovies(state.query)
            }
        }
    }

    sealed class ViewState {
        object NoInternet : ViewState()
        data class WithInternet(val query: String) : ViewState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::searchJob.isInitialized) searchJob.cancel()
        _binding = null
    }
}
package com.ss.moviehub.ui.search

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ss.moviehub.R
import com.ss.moviehub.databinding.FragmentSearchBinding
import com.ss.moviehub.repository.search.SearchRepository.ResponseStatus.Failed
import com.ss.moviehub.repository.search.SearchRepository.ResponseStatus.Successful
import com.ss.moviehub.utils.showSnackBar
import com.ss.moviehub.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels()
    private var capabilities: NetworkCapabilities? = null
    private lateinit var searchJob: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        searchMovie()
        getSearchedMovies()
    }

    private fun init() {
        val lastSearch = viewModel.getLastSearch()
        binding.searchResultFor.text = getString(R.string.search_result_for, lastSearch)

        val manager = requireActivity().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        capabilities = manager.getNetworkCapabilities(manager.activeNetwork)

        if (capabilities == null) checkNetwork(isConnected = false)
        else checkNetwork(isConnected = true, query = lastSearch)
    }

    private fun checkNetwork(isConnected: Boolean, query: String = ""): Unit = if (isConnected) {
        binding.noNetwork.visibility       = View.GONE
        binding.search.visibility          = View.VISIBLE
        binding.searchList.visibility      = View.VISIBLE
        binding.searchResultFor.visibility = View.VISIBLE
        if (query.isNotEmpty()) { viewModel.getSearchMovies(query); Unit } else Unit
    } else {
        binding.search.visibility          = View.GONE
        binding.searchList.visibility      = View.GONE
        binding.searchResultFor.visibility = View.GONE
        binding.noNetwork.visibility       = View.VISIBLE
        binding.noNetworkButton.setOnClickListener { init() }
    }

    private fun searchMovie() = binding.search.setOnQueryTextListener(object : OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            viewModel.saveLastSearch(query)
            binding.searchResultFor.text = getString(R.string.search_result_for, query)

            if (capabilities == null) checkNetwork(isConnected = false)
            else checkNetwork(isConnected = true, query = query)
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean = false
    })

    private fun getSearchedMovies() {
        searchJob = lifecycleScope.launch(Dispatchers.Main) {
            viewModel.searchedMovies.collect { status ->
                when (status) {
                    is Failed -> requireView().showSnackBar(status.message)
                    is Successful -> binding.searchList.adapter = SearchAdapter(status.movieList)
                }
            }
        }
    }

    override fun onDestroyView() {
        if (::searchJob.isInitialized) searchJob.cancel()
        super.onDestroyView()
    }
}
package com.ss.moviehub.ui.movies

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ss.moviehub.R
import com.ss.moviehub.databinding.FragmentMoviesBinding
import com.ss.moviehub.repository.movies.MoviesRepository.ResponseStatus.Failed
import com.ss.moviehub.repository.movies.MoviesRepository.ResponseStatus.Successful
import com.ss.moviehub.utils.navigateTo
import com.ss.moviehub.utils.showSnackBar
import com.ss.moviehub.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val binding by viewBinding(FragmentMoviesBinding::bind)
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var popularJob: Job
    private lateinit var topRatedJob: Job
    private lateinit var upcomingJop: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setHasOptionsMenu(true)
        val manager = requireActivity().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        if (capabilities != null) checkNetwork(isConnected = true) else checkNetwork(isConnected = false)
    }

    private fun checkNetwork(isConnected: Boolean): Unit = if (isConnected) {
        binding.noNetwork.visibility = View.GONE
        binding.scrollView.visibility = View.VISIBLE
        getMovies()
    } else {
        binding.scrollView.visibility = View.GONE
        binding.noNetwork.visibility = View.VISIBLE
        binding.noNetworkButton.setOnClickListener { init() }
    }

    private fun getMovies() {
        popularJob = lifecycleScope.launchWhenCreated {
            viewModel.popularMovies.collect { status ->
                when (status) {
                    is Failed -> requireView().showSnackBar(status.message)
                    is Successful -> binding.popularList.adapter = MovieAdapter(status.movieList)
                }
            }
        }

        topRatedJob = lifecycleScope.launchWhenCreated {
            viewModel.topRatedMovies.collect { status ->
                when (status) {
                    is Failed -> requireView().showSnackBar(status.message)
                    is Successful -> binding.topRatedList.adapter = MovieAdapter(status.movieList)
                }
            }
        }

        upcomingJop = lifecycleScope.launchWhenCreated {
            viewModel.upcomingMovies.collect { status ->
                when (status) {
                    is Failed -> requireView().showSnackBar(status.message)
                    is Successful -> binding.upcomingList.adapter = MovieAdapter(status.movieList)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movies_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings){
            val directions = MoviesFragmentDirections
            val action = directions.actionMoviesFragmentToSettingsFragment()
            findNavController().navigateTo(action, R.id.moviesFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        if (::popularJob.isInitialized) popularJob.cancel()
        if (::topRatedJob.isInitialized) topRatedJob.cancel()
        if (::upcomingJop.isInitialized) upcomingJop.cancel()
        super.onDestroyView()
    }
}
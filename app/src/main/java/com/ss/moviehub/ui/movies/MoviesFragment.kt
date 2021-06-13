package com.ss.moviehub.ui.movies

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ss.moviehub.R
import com.ss.moviehub.databinding.FragmentMoviesBinding
import com.ss.moviehub.repository.MoviesRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var popularJob: Job
    private lateinit var topRatedJob: Job
    private lateinit var upcomingJop: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        getMovies()

        return binding.root
    }

    private fun getMovies() {
        popularJob = lifecycleScope.launchWhenCreated {
            viewModel.getPopularMovie().collect { status ->
                when (status) {
                    is MoviesRepository.MoviesStatus.MoviesFailed ->
                        Snackbar.make(requireView(), status.message, Snackbar.LENGTH_SHORT).show()
                    is MoviesRepository.MoviesStatus.MoviesSuccessful -> {
                        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        binding.popularList.layoutManager = layoutManager
                        binding.popularList.adapter = MovieAdapter(status.movieList)
                    }
                }
            }
        }

        topRatedJob = lifecycleScope.launchWhenCreated {
            viewModel.getTopRatedMovie().collect { status ->
                when (status) {
                    is MoviesRepository.MoviesStatus.MoviesFailed ->
                        Snackbar.make(requireView(), status.message, Snackbar.LENGTH_SHORT).show()
                    is MoviesRepository.MoviesStatus.MoviesSuccessful -> {
                        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        binding.topRatedList.layoutManager = layoutManager
                        binding.topRatedList.adapter = MovieAdapter(status.movieList)
                    }
                }
            }
        }

        upcomingJop = lifecycleScope.launchWhenCreated {
            viewModel.getUpcomingMovie().collect { status ->
                when (status) {
                    is MoviesRepository.MoviesStatus.MoviesFailed ->
                        Snackbar.make(requireView(), status.message, Snackbar.LENGTH_SHORT).show()
                    is MoviesRepository.MoviesStatus.MoviesSuccessful -> {
                        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        binding.upcomingList.layoutManager = layoutManager
                        binding.upcomingList.adapter = MovieAdapter(status.movieList)
                    }
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
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        popularJob.cancel()
        topRatedJob.cancel()
        upcomingJop.cancel()
        _binding = null
    }
}
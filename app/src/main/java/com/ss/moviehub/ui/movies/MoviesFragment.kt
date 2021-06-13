package com.ss.moviehub.ui.movies

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Build
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
import com.ss.moviehub.ui.movies.MoviesFragment.ViewState.NO_INTERNET
import com.ss.moviehub.ui.movies.MoviesFragment.ViewState.WITH_INTERNET
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

        init()

        return binding.root
    }

    private fun init() {
        setHasOptionsMenu(true)
        val manager = requireActivity().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
            if (capabilities != null) customView(WITH_INTERNET) else customView(NO_INTERNET)
        } else if (manager.activeNetworkInfo != null)  customView(WITH_INTERNET) else customView(NO_INTERNET)
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

    private fun customView(state: ViewState) {
        when (state) {
            NO_INTERNET -> {
                binding.popularHeader.visibility = View.GONE
                binding.popularMessage.visibility = View.GONE
                binding.popularList.visibility = View.GONE
                binding.topRatedHeader.visibility = View.GONE
                binding.topRatedMessage.visibility = View.GONE
                binding.topRatedList.visibility = View.GONE
                binding.upcomingHeader.visibility = View.GONE
                binding.upcomingMessage.visibility = View.GONE
                binding.upcomingList.visibility = View.GONE
                binding.noNetwork.visibility = View.VISIBLE
                binding.noNetwork.setOnClickListener { init() }
            }
            WITH_INTERNET -> {
                binding.noNetwork.visibility = View.GONE
                binding.popularHeader.visibility = View.VISIBLE
                binding.popularMessage.visibility = View.VISIBLE
                binding.popularList.visibility = View.VISIBLE
                binding.topRatedHeader.visibility = View.VISIBLE
                binding.topRatedMessage.visibility = View.VISIBLE
                binding.topRatedList.visibility = View.VISIBLE
                binding.upcomingHeader.visibility = View.VISIBLE
                binding.upcomingMessage.visibility = View.VISIBLE
                binding.upcomingList.visibility = View.VISIBLE
                getMovies()
            }
        }
    }

    enum class ViewState {
        NO_INTERNET,
        WITH_INTERNET
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
        if (::popularJob.isInitialized) popularJob.cancel()
        if (::topRatedJob.isInitialized) topRatedJob.cancel()
        if (::upcomingJop.isInitialized) upcomingJop.cancel()
        _binding = null
    }
}
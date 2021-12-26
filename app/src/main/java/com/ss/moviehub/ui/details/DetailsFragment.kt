package com.ss.moviehub.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.ss.moviehub.R
import com.ss.moviehub.databinding.FragmentDetailsBinding
import com.ss.moviehub.repository.details.DetailsRepository.*
import com.ss.moviehub.repository.details.DetailsRepository.DetailsStatus.*
import com.ss.moviehub.utils.showSnackBar
import com.ss.moviehub.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel: DetailsViewModel by viewModels()
    private val arguments by navArgs<DetailsFragmentArgs>()
    private lateinit var detailsJob: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        bindMovieDetails()
        checkIsMovieInLibrary()
    }

    private fun init() {
        viewModel.checkIsOnLibrary(arguments.movie.id)
    }

    private fun bindMovieDetails() {
        binding.title.text       = arguments.movie.title
        binding.overview.text    = arguments.movie.overview
        binding.releaseDate.text = arguments.movie.releaseDate

        binding.poster.load("https://image.tmdb.org/t/p/w342${arguments.movie.posterPath}")
        binding.backdrop.load("https://image.tmdb.org/t/p/w342${arguments.movie.backdropPath}")

        arguments.movie.voteAverage.let {
            binding.ratingBar.rating  = it.toFloat()
            binding.ratingNumber.text = it.toString()
        }
    }

    private fun checkIsMovieInLibrary() {
        detailsJob = lifecycleScope.launch(Dispatchers.Main) {
            viewModel.checkIsOnLibrary.collect {
                if (it !is CheckResult) return@collect

                if (!it.isOnLibrary) {
                    binding.movieAction.text = getString(R.string.add_to_library)
                    binding.movieAction.setOnClickListener {
                        viewModel.addMovie(arguments.movie)
                        requireView().showSnackBar(getString(R.string.successfully_added))
                    }
                } else {
                    binding.movieAction.text = getString(R.string.delete_from_library)
                    binding.movieAction.setOnClickListener {
                        viewModel.removeMovie(arguments.movie)
                        requireView().showSnackBar(
                            message = getString(R.string.successfully_deleted),
                            actionMessage = getString(R.string.undo)
                        ) { viewModel.addMovie(arguments.movie) }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        if (::detailsJob.isInitialized) detailsJob.cancel()
        super.onDestroyView()
    }
}
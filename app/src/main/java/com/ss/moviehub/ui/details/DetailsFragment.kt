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
import com.ss.moviehub.utils.showSnackBar
import com.ss.moviehub.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel: DetailsViewModel by viewModels()
    private val arguments by navArgs<DetailsFragmentArgs>()
    private lateinit var detailsJob: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindMovieDetails()
        addOrRemoveMovie()
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

    private fun addOrRemoveMovie() {
        addToLibrary()
        detailsJob = lifecycleScope.launchWhenCreated {
            viewModel.getLibraryMovies().collect { libraryList ->
                if (libraryList.isNotEmpty()) libraryList.forEach {
                    if (it.id == arguments.movie.id) deleteFromLibrary()
                }
            }
        }
    }

    private fun addToLibrary() {
        binding.deleteFromLibrary.visibility = View.INVISIBLE
        binding.addToLibrary.visibility      = View.VISIBLE

        binding.addToLibrary.setOnClickListener {
            arguments.movie.added = true
            viewModel.addMovieToLibrary(arguments.movie)
            requireView().showSnackBar(getString(R.string.successfully_added))
            addOrRemoveMovie()
        }
    }

    private fun deleteFromLibrary() {
        binding.addToLibrary.visibility      = View.INVISIBLE
        binding.deleteFromLibrary.visibility = View.VISIBLE

        binding.deleteFromLibrary.setOnClickListener {
            viewModel.deleteMovieFromLibrary(arguments.movie)

            requireView().showSnackBar(
                message = getString(R.string.successfully_deleted),
                actionMessage = getString(R.string.undo)
            ) { viewModel.addMovieToLibrary(arguments.movie) }
            addOrRemoveMovie()
        }
    }

    override fun onDestroyView() {
        if (::detailsJob.isInitialized) detailsJob.cancel()
        super.onDestroyView()
    }
}
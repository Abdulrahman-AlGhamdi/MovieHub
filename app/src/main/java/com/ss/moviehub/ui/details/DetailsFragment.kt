package com.ss.moviehub.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.ss.moviehub.R
import com.ss.moviehub.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()
    private val arguments by navArgs<DetailsFragmentArgs>()
    private lateinit var detailsJob: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        bindMovieDetails()
        addOrRemoveMovie()

        return binding.root
    }

    private fun bindMovieDetails() {
        binding.title.text = arguments.movie.title
        binding.overview.text = arguments.movie.overview
        binding.releaseDate.text = arguments.movie.releaseDate
        binding.backdrop.load("https://image.tmdb.org/t/p/w342${arguments.movie.backdropPath}")
        binding.poster.load("https://image.tmdb.org/t/p/w342${arguments.movie.posterPath}")
        arguments.movie.voteAverage?.let {
            binding.ratingNumber.text = it.toString()
            binding.ratingBar.rating = it.toFloat()
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
        binding.addToLibrary.visibility = View.VISIBLE
        binding.addToLibrary.setOnClickListener {
            arguments.movie.added = true
            viewModel.addMovieToLibrary(arguments.movie)
            Snackbar.make(requireView(), getString(R.string.successfully_added), Snackbar.LENGTH_SHORT).apply {
                this.setAnchorView(R.id.navigation_bar)
            }.show()
            addOrRemoveMovie()
        }
    }

    private fun deleteFromLibrary() {
        binding.addToLibrary.visibility = View.INVISIBLE
        binding.deleteFromLibrary.visibility = View.VISIBLE
        binding.deleteFromLibrary.setOnClickListener {
            arguments.movie.added = false
            viewModel.deleteMovieFromLibrary(arguments.movie)
            Snackbar.make(requireView(), R.string.successfully_deleted, Snackbar.LENGTH_SHORT).apply {
                this.setAction(R.string.undo) {
                    viewModel.addMovieToLibrary(arguments.movie)
                    arguments.movie.added = true
                }
                this.setAnchorView(R.id.navigation_bar)
            }.show()
            addOrRemoveMovie()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailsJob.cancel()
        _binding = null
    }
}
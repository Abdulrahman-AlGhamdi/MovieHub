package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.snackbar.Snackbar
import com.ss.moviehub.R
import com.ss.moviehub.UI.MainActivity
import com.ss.moviehub.UI.ViewModel.MovieViewModel

class DetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private lateinit var title: TextView
    private lateinit var overview: TextView
    private lateinit var releaseDate: TextView
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var ratingNumber: TextView
    private lateinit var rating: RatingBar
    private lateinit var addToLibrary: Button
    private lateinit var deleteFromLibrary: Button
    private lateinit var viewModel: MovieViewModel
    private val arguments by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindMovieDetails()
        addToLibrary()
        deleteFromLibrary()
    }

    private fun initViews() {
        viewModel = (activity as MainActivity).viewModel
        title = requireView().findViewById(R.id.movie_title)
        overview = requireView().findViewById(R.id.movie_overview)
        releaseDate = requireView().findViewById(R.id.movie_release_date)
        backdrop = requireView().findViewById(R.id.movie_backdrop)
        poster = requireView().findViewById(R.id.movie_poster)
        ratingNumber = requireView().findViewById(R.id.rating_number)
        rating = requireView().findViewById(R.id.movie_rating_bar)
        addToLibrary = requireView().findViewById(R.id.add_to_library)
        deleteFromLibrary = requireView().findViewById(R.id.delete_from_library)
    }

    private fun bindMovieDetails() {
        title.text = arguments.selectedMovie.title
        overview.text = arguments.selectedMovie.overview
        releaseDate.text = arguments.selectedMovie.release_date
        Glide.with(backdrop)
            .load("https://image.tmdb.org/t/p/w342${arguments.selectedMovie.backdrop_path}")
            .transform(CenterCrop())
            .into(backdrop)
        Glide.with(poster)
            .load("https://image.tmdb.org/t/p/w342${arguments.selectedMovie.poster_path}")
            .transform(CenterCrop())
            .into(poster)

        ratingNumber.text = arguments.selectedMovie.vote_average.toString()
        rating.rating = arguments.selectedMovie.vote_average.toFloat() / 2
    }

    private fun addToLibrary() {
        if (!arguments.selectedMovie.added) {
            addToLibrary.visibility = View.VISIBLE
            addToLibrary.setOnClickListener {
                if (arguments.selectedMovie.title == null)
                    arguments.selectedMovie.title = ""

                if (arguments.selectedMovie.overview == null)
                    arguments.selectedMovie.overview = ""

                if (arguments.selectedMovie.release_date == null)
                    arguments.selectedMovie.release_date = ""

                if (arguments.selectedMovie.backdrop_path == null)
                    arguments.selectedMovie.backdrop_path = ""

                if (arguments.selectedMovie.poster_path == null)
                    arguments.selectedMovie.poster_path = ""

                if (arguments.selectedMovie.vote_average == null)
                    arguments.selectedMovie.vote_average = 0.0
                arguments.selectedMovie.added = true
                viewModel.addMovieToLibrary(arguments.selectedMovie)
                Snackbar.make(requireView(), "Added to Library", Snackbar.LENGTH_SHORT).show()
                when (arguments.fragment) {
                    "MovieFragment" -> findNavController().navigate(R.id.action_detailsFragment_to_moviesFragment)
                    "SearchFragment" -> findNavController().navigate(R.id.action_detailsFragment_to_searchFragment)
                    "LibraryFragment" -> findNavController().navigate(R.id.action_detailsFragment_to_libraryFragment)
                }
            }
        } else {
            addToLibrary.visibility = View.GONE
        }
    }

    private fun deleteFromLibrary() {
        if (arguments.selectedMovie.added) {
            deleteFromLibrary.visibility = View.VISIBLE
            deleteFromLibrary.setOnClickListener {
                arguments.selectedMovie.added = false
                viewModel.deleteMovieFromLibrary(arguments.selectedMovie)
                findNavController().navigate(R.id.action_detailsFragment_to_libraryFragment)
                Snackbar.make(requireView(), "Movie Successfully Deleted", Snackbar.LENGTH_SHORT)
                    .setAction("Undo") {
                        viewModel.addMovieToLibrary(arguments.selectedMovie)
                        arguments.selectedMovie.added = true
                    }
                    .show()
            }
        } else {
            deleteFromLibrary.visibility = View.GONE
        }
    }
}
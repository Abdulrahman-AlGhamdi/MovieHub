package com.ss.moviehub.UI.Fragments

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ss.moviehub.R
import kotlinx.android.synthetic.main.fragment_movie_details.*

class DetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val arguments by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindMovieDetails()
        addToLibrary()
    }

    private fun bindMovieDetails() {
        movie_title.text = arguments.selectedMovie.title
        movie_overview.text = arguments.selectedMovie.overview
        movie_release_date.text = arguments.selectedMovie.release_date
        Glide.with(movie_backdrop)
            .load("https://image.tmdb.org/t/p/w342${arguments.selectedMovie.backdrop_path}")
            .transform(CenterCrop())
            .into(movie_backdrop)
        Glide.with(movie_poster)
            .load("https://image.tmdb.org/t/p/w342${arguments.selectedMovie.poster_path}")
            .transform(CenterCrop())
            .into(movie_poster)

        rating_number.text = arguments.selectedMovie.vote_average.toString()
        movie_rating_star.rating = (arguments.selectedMovie.vote_average.toFloat()) / 2
    }

//    private fun checkIfAdded() {
//        if (arguments.selectedMovie.added) {
//            add_to_library.visibility = View.INVISIBLE
//        } else {
//            add_to_library.visibility = View.VISIBLE
//        }
//    }

    private fun addToLibrary() {
        add_to_library.setOnClickListener {
            arguments.selectedMovie.added = true
            libraryListHolder.add(arguments.selectedMovie)
            Toast.makeText(context, "Added to Library", Toast.LENGTH_SHORT).show()
        }
    }
}
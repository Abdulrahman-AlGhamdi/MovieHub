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

    private lateinit var title: TextView
    private lateinit var overview: TextView
    private lateinit var releaseDate: TextView
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var ratingNumber: TextView
    private lateinit var rating: RatingBar
    private lateinit var addToLibrary: Button
    private val arguments by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        bindMovieDetails()
        addToLibrary()
    }

    private fun initViews() {
        title = view?.findViewById(R.id.movie_title)!!
        overview = view?.findViewById(R.id.movie_overview)!!
        releaseDate = view?.findViewById(R.id.movie_release_date)!!
        backdrop = view?.findViewById(R.id.movie_backdrop)!!
        poster = view?.findViewById(R.id.movie_poster)!!
        ratingNumber = view?.findViewById(R.id.rating_number)!!
        rating = view?.findViewById(R.id.movie_rating_bar)!!
        addToLibrary = view?.findViewById(R.id.add_to_library)!!
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
        rating.rating = (arguments.selectedMovie.vote_average.toFloat()) / 2
    }

    private fun addToLibrary() {
        addToLibrary.setOnClickListener {
            libraryListHolder.add(arguments.selectedMovie)
            Toast.makeText(context, "Added to Library", Toast.LENGTH_SHORT).show()
        }
    }
}
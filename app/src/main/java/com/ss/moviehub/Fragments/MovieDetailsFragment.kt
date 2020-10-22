package com.ss.moviehub.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ss.moviehub.R

class MovieDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        val bundle = arguments

        val overView = view.findViewById<TextView>(R.id.movie_overview)
        val releaseDate = view.findViewById<TextView>(R.id.movie_release_date)
        val backdrop = view.findViewById<ImageView>(R.id.movie_backdrop)
        val poster = view.findViewById<ImageView>(R.id.movie_poster)
        val title = view.findViewById<TextView>(R.id.movie_title)
        val ratingStar = view.findViewById<RatingBar>(R.id.movie_rating_star)
        val ratingNumber = view.findViewById<TextView>(R.id.rating_number)

        overView.text = bundle?.getString("overview")
        releaseDate.text = bundle?.getString("releaseDate")
        Glide.with(backdrop)
            .load("https://image.tmdb.org/t/p/w342${bundle?.getString("backdrop")}")
            .transform(CenterCrop())
            .into(backdrop)
        Glide.with(poster)
            .load("https://image.tmdb.org/t/p/w342${bundle?.getString("poster")}")
            .transform(CenterCrop())
            .into(poster)
        title.text = bundle?.getString("title")
        ratingNumber.text = bundle?.getString("voteAverage")
        ratingStar.rating = (bundle?.getString("voteAverage")!!.toFloat()) / 2

        return view
    }
}
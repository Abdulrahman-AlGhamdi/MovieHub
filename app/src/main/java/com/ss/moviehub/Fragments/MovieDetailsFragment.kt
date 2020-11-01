package com.ss.moviehub.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ss.moviehub.MainActivity
import com.ss.moviehub.Models.Result
import com.ss.moviehub.R


class MovieDetailsFragment : Fragment() {

    private lateinit var detailsView: View
    private lateinit var bundle: Bundle
    private lateinit var overView: TextView
    private lateinit var addToLibrary: Button
    private lateinit var releaseDate: TextView
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var ratingStar: RatingBar
    private lateinit var ratingNumber: TextView
    private lateinit var libraryFragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        detailsView = inflater.inflate(R.layout.fragment_movie_details, container, false)

        initViews()
        bindMovieDetails()
        addToLibrary()

        return detailsView
    }

    private fun initViews() {
        overView = detailsView.findViewById(R.id.movie_overview)
        releaseDate = detailsView.findViewById(R.id.movie_release_date)
        backdrop = detailsView.findViewById(R.id.movie_backdrop)
        poster = detailsView.findViewById(R.id.movie_poster)
        title = detailsView.findViewById(R.id.movie_title)
        ratingStar = detailsView.findViewById(R.id.movie_rating_star)
        ratingNumber = detailsView.findViewById(R.id.rating_number)
        addToLibrary = detailsView.findViewById(R.id.add_to_library)
        libraryFragment = LibraryFragment()
    }

    private fun bindMovieDetails() {
        if (this.arguments != null) {
            bundle = this.arguments!!
            val details: Result? = bundle.getParcelable("details")
            overView.text = details?.overview
            releaseDate.text = details?.release_date
            Glide.with(backdrop)
                .load("https://image.tmdb.org/t/p/w342${details?.backdrop_path}")
                .transform(CenterCrop())
                .into(backdrop)
            Glide.with(poster)
                .load("https://image.tmdb.org/t/p/w342${details?.poster_path}")
                .transform(CenterCrop())
                .into(poster)
            title.text = details?.title
            ratingNumber.text = details?.vote_average.toString()
            ratingStar.rating = (details?.vote_average!!.toFloat()) / 2
        }
    }

    private fun addToLibrary() {
        addToLibrary.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            bundle.putParcelable("library", bundle.getParcelable("details"))
            intent.putExtra("libraryIntent", bundle)
            startActivity(intent)
            Toast.makeText(context, "Added to Library", Toast.LENGTH_SHORT).show()
        }
    }
}
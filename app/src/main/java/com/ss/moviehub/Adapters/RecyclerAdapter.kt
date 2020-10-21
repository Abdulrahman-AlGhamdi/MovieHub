package com.ss.moviehub.Adapters

import android.content.Context
import android.icu.text.CaseMap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ss.moviehub.Fragments.MovieDetailsFragment
import com.ss.moviehub.Fragments.MoviesFragment
import com.ss.moviehub.R

class RecyclerAdapter(
    private var posterPath: List<String>,
    private var title: List<String>,
    private var backdrop: List<String>,
    private var releaseDate: List<String>,
    private var overview: List<String>,
    private var voteAverage: List<Double>,
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posterPath.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val bundle = Bundle()
        val movieDetailsFragment: Fragment = MovieDetailsFragment()

        Glide.with(holder.poster)
            .load("https://image.tmdb.org/t/p/w342${posterPath[position]}")
            .transform(CenterCrop())
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            bundle.putString("poster", posterPath[position])
            bundle.putString("title", title[position])
            bundle.putString("backdrop", backdrop[position])
            bundle.putString("releaseDate", releaseDate[position])
            bundle.putString("overview", overview[position])
            bundle.putString("voteAverage", voteAverage[position].toString())
            movieDetailsFragment.arguments = bundle
            (holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, movieDetailsFragment)
                .addToBackStack(null).commit()
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
}
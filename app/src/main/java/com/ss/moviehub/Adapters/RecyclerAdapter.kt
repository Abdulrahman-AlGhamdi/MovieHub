package com.ss.moviehub.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ss.moviehub.Fragments.LibraryFragmentDirections
import com.ss.moviehub.Fragments.MoviesFragmentDirections
import com.ss.moviehub.Fragments.SearchFragmentDirections
import com.ss.moviehub.Models.Result
import com.ss.moviehub.R
import kotlinx.android.synthetic.main.item_movie.view.*

class RecyclerAdapter(private val movieItems: List<Result>, private val fragmentName: String) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("resultMovie", "onBindViewHolder: has been called")

        val movieItem = movieItems[position]

        Glide.with(holder.poster)
            .load("https://image.tmdb.org/t/p/w342${movieItem.poster_path}")
            .transform(CenterCrop())
            .into(holder.poster)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        init {
            itemView.item_movie_poster.setOnClickListener {
                val position = adapterPosition

                val movieAction =
                    MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(movieItems[position])
                val searchAction =
                    SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movieItems[position])
                val libraryAction =
                    LibraryFragmentDirections.actionLibraryFragmentToDetailsFragment(movieItems[position])

                if (fragmentName == "MovieFragment")
                    itemView.findNavController().navigate(movieAction)

                if (fragmentName == "SearchFragment")
                    itemView.findNavController().navigate(searchAction)

                if (fragmentName == "LibraryFragment")
                    itemView.findNavController().navigate(libraryAction)
            }
        }
    }
}
package com.ss.moviehub.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ss.moviehub.Models.Result
import com.ss.moviehub.R
import com.ss.moviehub.UI.Fragments.LibraryFragmentDirections
import com.ss.moviehub.UI.Fragments.MoviesFragmentDirections
import com.ss.moviehub.UI.Fragments.SearchFragmentDirections
import kotlinx.android.synthetic.main.row_item_movie.view.*

class MovieAdapter(private val fragmentName: String) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        init {
            itemView.item_movie_poster.setOnClickListener {
                val position = adapterPosition

                val movieAction =
                    MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                        differ.currentList[position],
                        "MovieFragment"
                    )
                val searchAction =
                    SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                        differ.currentList[position],
                        "MovieFragment"
                    )

                if (fragmentName == "MovieFragment")
                    itemView.findNavController().navigate(movieAction)

                if (fragmentName == "SearchFragment")
                    itemView.findNavController().navigate(searchAction)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_movie, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movieItem = differ.currentList[position]

        Glide.with(holder.poster)
            .load("https://image.tmdb.org/t/p/w342${movieItem.poster_path}")
            .transform(CenterCrop())
            .into(holder.poster)
    }
}
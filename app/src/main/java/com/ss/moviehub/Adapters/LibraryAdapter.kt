package com.ss.moviehub.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.ss.moviehub.Models.Result
import com.ss.moviehub.R
import com.ss.moviehub.UI.Fragments.LibraryFragmentDirections
import kotlinx.android.synthetic.main.row_library_item.view.*

class LibraryAdapter : RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_library_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryAdapter.ViewHolder, position: Int) {
        val movieItem = differ.currentList[position]

        holder.title.text = movieItem.title
        holder.releaseDate.text = movieItem.release_date
        holder.language.text = "Votes: ${movieItem.vote_count.toString()}"
        holder.ratingBar.rating = movieItem.vote_average.toFloat() / 2

        Glide.with(holder.poster)
            .load("https://image.tmdb.org/t/p/w342${movieItem.poster_path}")
            .transform(CenterCrop())
            .into(holder.poster)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.item_library_title)
        val poster: ImageView = itemView.findViewById(R.id.item_library_poster)
        val language: TextView = itemView.findViewById(R.id.item_library_language)
        val releaseDate: TextView = itemView.findViewById(R.id.item_library_release)
        val ratingBar: RatingBar = itemView.findViewById(R.id.item_library_rating_bar)

        init {
            itemView.library_movie_item.setOnClickListener {
                val position = adapterPosition

                val libraryAction =
                    LibraryFragmentDirections.actionLibraryFragmentToDetailsFragment(
                        differ.currentList[position],
                        "LibraryFragment"
                    )

                itemView.findNavController().navigate(libraryAction)
            }
        }
    }
}
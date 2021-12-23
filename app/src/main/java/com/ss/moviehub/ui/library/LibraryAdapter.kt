package com.ss.moviehub.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ss.moviehub.R
import com.ss.moviehub.databinding.RowLibraryItemBinding
import com.ss.moviehub.models.Result
import com.ss.moviehub.utils.navigateTo

class LibraryAdapter : RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    })

    inner class ViewHolder(
        private val binding: RowLibraryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Result) {
            binding.title.text = movie.title
            binding.releaseDate.text = movie.releaseDate
            binding.language.text = "Votes: ${movie.voteCount}"
            movie.voteAverage?.let { binding.ratingBar.rating = it.toFloat() / 2 }
            binding.poster.load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
            binding.root.setOnClickListener {
                val directions = LibraryFragmentDirections
                val action = directions.actionLibraryFragmentToDetailsFragment(movie)
                itemView.findNavController().navigateTo(action, R.id.libraryFragment)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowLibraryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size
}
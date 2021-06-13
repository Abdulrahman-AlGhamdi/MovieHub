package com.ss.moviehub.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ss.moviehub.databinding.RowSearchItemBinding
import com.ss.moviehub.models.Result

class SearchAdapter(
    private val movieList: List<Result>
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: RowSearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Result) {
            binding.poster.load("https://image.tmdb.org/t/p/w342${movie.posterPath}")

            binding.root.setOnClickListener {
                val directions = SearchFragmentDirections
                val action = directions.actionSearchFragmentToDetailsFragment(movie)
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }
}
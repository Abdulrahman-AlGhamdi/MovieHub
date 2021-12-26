package com.ss.moviehub.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ss.moviehub.R
import com.ss.moviehub.databinding.RowMovieItemBinding
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.utils.navigateTo

class MovieAdapter(
    private val movieList: List<Result>
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: RowMovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Result) {
            binding.poster.load("https://image.tmdb.org/t/p/w342${movie.posterPath}")

            binding.root.setOnClickListener {
                val directions = MoviesFragmentDirections
                val action     = directions.actionMoviesFragmentToDetailsFragment(movie)
                itemView.findNavController().navigateTo(action, R.id.moviesFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }
}
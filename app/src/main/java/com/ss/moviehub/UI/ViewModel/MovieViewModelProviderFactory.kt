package com.ss.moviehub.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ss.moviehub.Repository.MovieRepository

class MovieViewModelProviderFactory(private val repository: MovieRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(repository) as T
    }
}
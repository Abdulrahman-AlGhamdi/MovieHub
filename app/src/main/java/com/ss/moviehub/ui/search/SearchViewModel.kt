package com.ss.moviehub.ui.search

import androidx.lifecycle.ViewModel
import com.ss.moviehub.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    suspend fun getSearchMovies(search: String, pageNumber: Int) =
        moviesRepository.getSearchedMovies(search, pageNumber)
}
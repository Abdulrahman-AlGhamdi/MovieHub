package com.ss.moviehub.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.repository.MoviesRepository
import com.ss.moviehub.repository.MoviesRepository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            moviesRepository.getPopularMovie(1)
            moviesRepository.getTopRatedMovie(1)
            moviesRepository.getUpcomingMovie(1)
        }
    }

    fun getPopularMovie() = moviesRepository.popularMovies

    fun getTopRatedMovie() = moviesRepository.topRatedMovies

    fun getUpcomingMovie() = moviesRepository.upcomingMovies
}
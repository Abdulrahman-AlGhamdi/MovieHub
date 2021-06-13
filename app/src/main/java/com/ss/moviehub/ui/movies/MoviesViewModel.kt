package com.ss.moviehub.ui.movies

import androidx.lifecycle.ViewModel
import com.ss.moviehub.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    suspend fun getPopularMovie() = moviesRepository.getPopularMovie(1)

    suspend fun getTopRatedMovie() = moviesRepository.getTopRatedMovie(1)

    suspend fun getUpcomingMovie() = moviesRepository.getUpcomingMovie(1)
}
package com.ss.moviehub.screen.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.repository.movies.MoviesRepository
import com.ss.moviehub.repository.movies.MoviesRepository.ResponseStatus
import com.ss.moviehub.repository.movies.MoviesRepository.ResponseStatus.Idle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<ResponseStatus>(Idle)
    val popularMovies = _popularMovies.asStateFlow()
    private val _topRatedMovies = MutableStateFlow<ResponseStatus>(Idle)
    val topRatedMovies = _topRatedMovies.asStateFlow()
    private val _upcomingMovies = MutableStateFlow<ResponseStatus>(Idle)
    val upcomingMovies = _upcomingMovies.asStateFlow()

    init {
        getPopularMovie()
        getTopRatedMovie()
        getUpcomingMovie()
    }

    private fun getPopularMovie(pageNumber: Int = 1) = viewModelScope.launch {
        moviesRepository.getPopularMovie(pageNumber).collect {
            _popularMovies.value = it
        }
    }

    private fun getTopRatedMovie(pageNumber: Int = 1) = viewModelScope.launch {
        moviesRepository.getTopRatedMovie(pageNumber).collect {
            _topRatedMovies.value = it
        }
    }

    private fun getUpcomingMovie(pageNumber: Int = 1) = viewModelScope.launch {
        moviesRepository.getUpcomingMovie(pageNumber).collect {
            _upcomingMovies.value = it
        }
    }
}
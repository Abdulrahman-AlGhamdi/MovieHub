package com.ss.moviehub.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.repository.details.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    private var _isInLibrary = MutableStateFlow(false)
    val isInLibrary = _isInLibrary.asStateFlow()

    fun checkIsInLibrary(movieId: Int?) = viewModelScope.launch {
        detailsRepository.isInLibrary(movieId).collect {
            _isInLibrary.value = it
        }
    }

    fun addMovieToLibrary(movie: Result?) = viewModelScope.launch {
        detailsRepository.addMovieToLibrary(movie)
    }

    fun removeMovie(movie: Result?) = viewModelScope.launch {
        detailsRepository.removeMovieFromLibrary(movie)
    }
}
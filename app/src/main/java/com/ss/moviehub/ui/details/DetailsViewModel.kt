package com.ss.moviehub.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.models.Result
import com.ss.moviehub.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    fun getLibraryMovies() = databaseRepository.getLibraryMovies()

    fun addMovieToLibrary(result: Result) {
        viewModelScope.launch { databaseRepository.addMovieToLibrary(result) }
    }

    fun deleteMovieFromLibrary(result: Result) {
        viewModelScope.launch { databaseRepository.deleteMovieLibrary(result) }
    }
}
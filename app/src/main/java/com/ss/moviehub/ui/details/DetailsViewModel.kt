package com.ss.moviehub.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.models.Result
import com.ss.moviehub.repository.library.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository,
) : ViewModel() {

    fun getLibraryMovies() = libraryRepository.getLibraryMovies()

    fun addMovieToLibrary(result: Result) {
        viewModelScope.launch { libraryRepository.addMovieToLibrary(result) }
    }

    fun deleteMovieFromLibrary(result: Result) {
        viewModelScope.launch { libraryRepository.deleteMovieLibrary(result) }
    }
}
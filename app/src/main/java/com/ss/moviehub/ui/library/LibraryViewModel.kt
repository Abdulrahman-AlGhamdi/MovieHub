package com.ss.moviehub.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.repository.library.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
) : ViewModel() {

    fun getLibraryMovies() = libraryRepository.getLibraryMovies()

    fun addMovie(result: Result) = viewModelScope.launch {
        libraryRepository.addMovie(result)
    }

    fun removeMovie(result: Result) = viewModelScope.launch {
        libraryRepository.removeMovie(result)
    }

    fun deleteAllMovies() = viewModelScope.launch {
        libraryRepository.deleteAllMovies()
    }
}
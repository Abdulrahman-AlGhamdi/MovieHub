package com.ss.moviehub.screen.library

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.repository.library.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val libraryRepository: LibraryRepository
) : ViewModel() {

    private val _libraryMovies = MutableStateFlow<List<Result>>(emptyList())
    val libraryList = _libraryMovies.asStateFlow()

    init {
        getLibraryMovies()
    }

    private fun getLibraryMovies() = viewModelScope.launch {
        libraryRepository.getLibraryMovies().collect {
            _libraryMovies.value = it
        }
    }

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
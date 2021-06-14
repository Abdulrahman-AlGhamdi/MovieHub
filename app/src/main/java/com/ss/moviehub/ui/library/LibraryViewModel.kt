package com.ss.moviehub.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.models.Result
import com.ss.moviehub.repository.DatabaseRepository
import com.ss.moviehub.ui.library.LibraryViewModel.LibraryMoviesState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    fun deleteAllMovies() {
        viewModelScope.launch { databaseRepository.deleteAllMovies() }
    }

    suspend fun getLibraryMovies() = flow {
        databaseRepository.getLibraryMovies().collect {
            if (it.isEmpty()) emit(EmptyList) else emit(LibraryList(it))
        }
    }.flowOn(Dispatchers.IO)

    fun addMovieToLibrary(result: Result) {
        viewModelScope.launch { databaseRepository.addMovieToLibrary(result) }
    }

    fun deleteMovieFromLibrary(result: Result) {
        viewModelScope.launch { databaseRepository.deleteMovieLibrary(result) }
    }

    sealed class LibraryMoviesState {
        object EmptyList : LibraryMoviesState()
        data class LibraryList(val libraryList: List<Result>) : LibraryMoviesState()
    }
}
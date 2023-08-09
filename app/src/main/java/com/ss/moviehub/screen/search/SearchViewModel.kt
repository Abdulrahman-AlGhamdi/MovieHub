package com.ss.moviehub.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.repository.search.SearchRepository
import com.ss.moviehub.repository.search.SearchRepository.ResponseStatus
import com.ss.moviehub.repository.search.SearchRepository.ResponseStatus.Idle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchedMovies = MutableStateFlow<ResponseStatus>(Idle)
    val searchedMovies = _searchedMovies.asStateFlow()

    fun getSearchMovies(search: String, pageNumber: Int = 1) = viewModelScope.launch {
        searchRepository.getSearchedMovies(search, pageNumber).collect {
            _searchedMovies.value = it
        }
    }

    fun saveLastSearch(search: String) = searchRepository.setLastSearch(search)

    fun getLastSearch(): String = searchRepository.getLastSearch()
}
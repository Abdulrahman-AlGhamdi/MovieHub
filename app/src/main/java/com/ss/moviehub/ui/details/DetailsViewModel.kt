package com.ss.moviehub.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.repository.details.DetailsRepository
import com.ss.moviehub.repository.details.DetailsRepository.*
import com.ss.moviehub.repository.details.DetailsRepository.DetailsStatus.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
) : ViewModel() {

    private var _checkIsOnLibrary = MutableStateFlow<DetailsStatus>(Idle)
    val checkIsOnLibrary = _checkIsOnLibrary.asStateFlow()

    fun checkIsOnLibrary(movieId: Int) = viewModelScope.launch {
        detailsRepository.checkIsOnLibrary(movieId).collect {
            _checkIsOnLibrary.value = it
        }
    }

    fun addMovie(result: Result) = viewModelScope.launch {
        detailsRepository.addMovie(result)
        checkIsOnLibrary(result.id)
    }

    fun removeMovie(result: Result) = viewModelScope.launch {
        detailsRepository.removeMovie(result)
        checkIsOnLibrary(result.id)
    }
}
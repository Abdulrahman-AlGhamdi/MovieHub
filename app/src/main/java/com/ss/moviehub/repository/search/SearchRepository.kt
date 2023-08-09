package com.ss.moviehub.repository.search

import com.ss.moviehub.data.models.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getSearchedMovies(search: String, pageNumber: Int): Flow<ResponseStatus>

    fun setLastSearch(search: String)

    fun getLastSearch(): String

    sealed class ResponseStatus {
        object Idle : ResponseStatus()
        object Loading : ResponseStatus()
        data class Failed(val message: String) : ResponseStatus()
        data class Successful(val movieList: List<Result>) : ResponseStatus()
    }
}
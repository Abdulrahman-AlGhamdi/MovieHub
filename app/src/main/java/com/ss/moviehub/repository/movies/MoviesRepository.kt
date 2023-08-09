package com.ss.moviehub.repository.movies

import com.ss.moviehub.data.models.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPopularMovie(pageNumber: Int): Flow<ResponseStatus>

    fun getTopRatedMovie(pageNumber: Int): Flow<ResponseStatus>

    fun getUpcomingMovie(pageNumber: Int): Flow<ResponseStatus>

    sealed interface ResponseStatus {
        object Idle : ResponseStatus
        object Loading : ResponseStatus
        data class Failed(val message: String) : ResponseStatus
        data class Successful(val movieList: List<Result>) : ResponseStatus
    }
}
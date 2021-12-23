package com.ss.moviehub.repository.movies

import com.ss.moviehub.data.api.MovieApiService
import com.ss.moviehub.models.Result
import com.ss.moviehub.repository.movies.MoviesRepository.ResponseStatus.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val apiService: MovieApiService
) {

    fun getPopularMovie(pageNumber: Int) = flow {
        this.emit(Loading)
        val response = apiService.getPopularMovie(pageNumber)
        if (!response.isSuccessful) this.emit(Failed(response.message()))
        else response.body()?.results?.let { this.emit(Successful(it)) }
    }.flowOn(Dispatchers.IO)

    fun getTopRatedMovie(pageNumber: Int) = flow {
        this.emit(Loading)
        val response = apiService.getTopRatedMovie(pageNumber)
        if (!response.isSuccessful) this.emit(Failed(response.message()))
        else response.body()?.results?.let { this.emit(Successful(it)) }
    }.flowOn(Dispatchers.IO)

    fun getUpcomingMovie(pageNumber: Int) = flow {
        this.emit(Loading)
        val response = apiService.getUpcomingMovie(pageNumber)
        if (!response.isSuccessful) this.emit(Failed(response.message()))
        else response.body()?.results?.let { this.emit(Successful(it)) }
    }.flowOn(Dispatchers.IO)

    sealed class ResponseStatus {
        object Idle                                        : ResponseStatus()
        object Loading                                     : ResponseStatus()
        data class Failed(val message: String)             : ResponseStatus()
        data class Successful(val movieList: List<Result>) : ResponseStatus()
    }
}
package com.ss.moviehub.repository.movies

import com.ss.moviehub.data.api.MovieApiService
import com.ss.moviehub.repository.movies.MoviesRepository.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MoviesRepository {

    override fun getPopularMovie(pageNumber: Int): Flow<ResponseStatus> = flow {
        this.emit(ResponseStatus.Loading)
        val response = apiService.getPopularMovie(pageNumber)
        if (!response.isSuccessful) this.emit(ResponseStatus.Failed(response.message()))
        else response.body()?.results?.let { this.emit(ResponseStatus.Successful(it)) }
    }.flowOn(Dispatchers.IO)

    override fun getTopRatedMovie(pageNumber: Int): Flow<ResponseStatus> = flow {
        this.emit(ResponseStatus.Loading)
        val response = apiService.getTopRatedMovie(pageNumber)
        if (!response.isSuccessful) this.emit(ResponseStatus.Failed(response.message()))
        else response.body()?.results?.let { this.emit(ResponseStatus.Successful(it)) }
    }.flowOn(Dispatchers.IO)

    override fun getUpcomingMovie(pageNumber: Int): Flow<ResponseStatus> = flow {
        this.emit(ResponseStatus.Loading)
        val response = apiService.getUpcomingMovie(pageNumber)
        if (!response.isSuccessful) this.emit(ResponseStatus.Failed(response.message()))
        else response.body()?.results?.let { this.emit(ResponseStatus.Successful(it)) }
    }.flowOn(Dispatchers.IO)
}
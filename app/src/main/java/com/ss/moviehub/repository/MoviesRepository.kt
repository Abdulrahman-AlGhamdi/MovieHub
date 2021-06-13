package com.ss.moviehub.repository

import com.ss.moviehub.api.MovieApiService
import com.ss.moviehub.models.Result
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val apiService: MovieApiService) {

    suspend fun getPopularMovie(pageNumber: Int) = flow {
        apiService.getPopularMovie(pageNumber).apply {
            if (this.isSuccessful && this.body() != null)
                this.body()!!.results.filter { !it.posterPath.isNullOrEmpty() }.apply {
                    emit(MoviesStatus.MoviesSuccessful(this)) }
            else emit(MoviesStatus.MoviesFailed(this.message()))
        }
    }

    suspend fun getTopRatedMovie(pageNumber: Int) = flow {
        apiService.getTopRatedMovie(pageNumber).apply {
            if (this.isSuccessful && this.body() != null)
                this.body()!!.results.filter { !it.posterPath.isNullOrEmpty() }.apply {
                    emit(MoviesStatus.MoviesSuccessful(this)) }
            else emit(MoviesStatus.MoviesFailed(this.message()))
        }
    }

    suspend fun getUpcomingMovie(pageNumber: Int) = flow {
        apiService.getUpcomingMovie(pageNumber).apply {
            if (this.isSuccessful && this.body() != null)
                this.body()!!.results.filter { !it.posterPath.isNullOrEmpty() }.apply {
                    emit(MoviesStatus.MoviesSuccessful(this)) }
            else emit(MoviesStatus.MoviesFailed(this.message()))
        }
    }

    suspend fun getSearchedMovies(search: String, pageNumber: Int) = flow {
        apiService.getSearchedMovie(search, pageNumber).apply {
            if (this.isSuccessful && this.body() != null) {
                val body = this.body()!!
                if (body.results.isEmpty()) emit(MoviesStatus.MoviesFailed("No Result"))
                else body.results.filter { !it.posterPath.isNullOrEmpty() }.apply {
                    if (this.isNotEmpty()) emit(MoviesStatus.MoviesSuccessful(this))
                    else emit(MoviesStatus.MoviesFailed("No Result"))
                }
            } else emit(MoviesStatus.MoviesFailed(this.message()))
        }
    }

    sealed class MoviesStatus {
        data class MoviesSuccessful(val movieList: List<Result>) : MoviesStatus()
        data class MoviesFailed(val message: String) : MoviesStatus()
    }
}
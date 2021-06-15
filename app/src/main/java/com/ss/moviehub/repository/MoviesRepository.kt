package com.ss.moviehub.repository

import android.content.Context
import com.ss.moviehub.R
import com.ss.moviehub.api.MovieApiService
import com.ss.moviehub.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val context: Context,
    private val apiService: MovieApiService
) {

    private val _popularMovies = MutableStateFlow<ResponseStatus>(ResponseStatus.Loading)
    val popularMovies: StateFlow<ResponseStatus> get() = _popularMovies
    private val _topRatedMovies = MutableStateFlow<ResponseStatus>(ResponseStatus.Loading)
    val topRatedMovies get() = _topRatedMovies
    private val _upcomingMovies = MutableStateFlow<ResponseStatus>(ResponseStatus.Loading)
    val upcomingMovies get() = _upcomingMovies

    suspend fun getPopularMovie(pageNumber: Int) = withContext(Dispatchers.IO) {
        apiService.getPopularMovie(pageNumber).apply {
            if (this.isSuccessful && this.body() != null)
                this.body()!!.results.filter { !it.posterPath.isNullOrEmpty() }.apply {
                    _popularMovies.value = (ResponseStatus.Successful(this))
                } else _popularMovies.value = (ResponseStatus.Failed(this.message()))
        }
    }

    suspend fun getTopRatedMovie(pageNumber: Int) = withContext(Dispatchers.IO) {
        apiService.getTopRatedMovie(pageNumber).apply {
            if (this.isSuccessful && this.body() != null)
                this.body()!!.results.filter { !it.posterPath.isNullOrEmpty() }.apply {
                    _topRatedMovies.value = (ResponseStatus.Successful(this))
                } else _topRatedMovies.value = (ResponseStatus.Failed(this.message()))
        }
    }

    suspend fun getUpcomingMovie(pageNumber: Int) = withContext(Dispatchers.IO) {
        apiService.getUpcomingMovie(pageNumber).apply {
            if (this.isSuccessful && this.body() != null)
                this.body()!!.results.filter { !it.posterPath.isNullOrEmpty() }.apply {
                    _upcomingMovies. value = (ResponseStatus.Successful(this))
                } else _upcomingMovies.value = (ResponseStatus.Failed(this.message()))
        }
    }

    suspend fun getSearchedMovies(search: String, pageNumber: Int) = flow {
        apiService.getSearchedMovie(search, pageNumber).apply {
            if (this.isSuccessful && this.body() != null) {
                val body = this.body()!!
                if (body.results.isEmpty()) emit(ResponseStatus.Failed(context.getString(R.string.error_no_result)))
                else body.results.filter { !it.posterPath.isNullOrEmpty() }.apply {
                    if (this.isNotEmpty()) emit(ResponseStatus.Successful(this))
                    else emit(ResponseStatus.Failed(context.getString(R.string.error_no_result)))
                }
            } else emit(ResponseStatus.Failed(this.message()))
        }
    }

    sealed class ResponseStatus {
        object Loading : ResponseStatus()
        data class Successful(val movieList: List<Result>) : ResponseStatus()
        data class Failed(val message: String) : ResponseStatus()
    }
}
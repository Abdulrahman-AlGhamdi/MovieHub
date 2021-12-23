package com.ss.moviehub.repository.search

import android.content.Context
import com.ss.moviehub.R
import com.ss.moviehub.data.api.MovieApiService
import com.ss.moviehub.models.Result
import com.ss.moviehub.repository.search.SearchRepository.ResponseStatus.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val context: Context,
    private val apiService: MovieApiService
) {

    suspend fun getSearchedMovies(search: String, pageNumber: Int) = flow {
        this.emit(Loading)
        val response = apiService.getSearchedMovie(search, pageNumber)
        if (!response.isSuccessful) this.emit(Failed(response.message()))
        else response.body()?.results?.filter { !it.posterPath.isNullOrEmpty() }?.let {
            if (it.isNotEmpty()) this.emit(Successful(it))
            else emit(Failed(context.getString(R.string.error_no_result)))
        }
    }.flowOn(Dispatchers.IO)

    sealed class ResponseStatus {
        object Idle                                        : ResponseStatus()
        object Loading                                     : ResponseStatus()
        data class Failed(val message: String)             : ResponseStatus()
        data class Successful(val movieList: List<Result>) : ResponseStatus()
    }
}
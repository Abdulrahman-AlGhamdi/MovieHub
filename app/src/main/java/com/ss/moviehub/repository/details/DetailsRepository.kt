package com.ss.moviehub.repository.details

import com.ss.moviehub.data.models.Result
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    fun isInLibrary(movieId: Int?): Flow<Boolean>

    suspend fun addMovieToLibrary(movie: Result?)

    suspend fun removeMovieFromLibrary(movie: Result?)
}
package com.ss.moviehub.repository.library

import com.ss.moviehub.data.models.Result
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun getLibraryMovies(): Flow<List<Result>>

    suspend fun addMovie(result: Result)

    suspend fun removeMovie(result: Result)

    suspend fun deleteAllMovies()
}
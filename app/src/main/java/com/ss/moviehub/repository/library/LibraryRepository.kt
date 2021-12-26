package com.ss.moviehub.repository.library

import com.ss.moviehub.data.database.MovieDao
import com.ss.moviehub.data.models.Result
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val moviesDao: MovieDao
) {

    fun getLibraryMovies() = moviesDao.getAllMovies()

    suspend fun addMovie(result: Result) = moviesDao.addAndUpdateMovie(result)

    suspend fun removeMovie(result: Result) = moviesDao.deleteMovie(result)

    suspend fun deleteAllMovies() = moviesDao.deleteAllMovies()
}
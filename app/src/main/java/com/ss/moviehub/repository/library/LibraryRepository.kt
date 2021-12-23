package com.ss.moviehub.repository.library

import com.ss.moviehub.data.database.MovieDao
import com.ss.moviehub.models.Result
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val moviesDao: MovieDao
) {

    fun getLibraryMovies() = moviesDao.getLibraryMovies()

    suspend fun addMovieToLibrary(result: Result) = moviesDao.addAndUpdateMovie(result)

    suspend fun deleteMovieLibrary(result: Result) = moviesDao.deleteMovie(result)

    suspend fun deleteAllMovies() = moviesDao.deleteAllMovies()
}
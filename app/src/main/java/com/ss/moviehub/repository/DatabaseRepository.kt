package com.ss.moviehub.repository

import com.ss.moviehub.database.MovieDao
import com.ss.moviehub.models.Result
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val moviesDao: MovieDao) {

    suspend fun addMovieToLibrary(result: Result) = moviesDao.addAndUpdateMovie(result)

    suspend fun deleteMovieLibrary(result: Result) = moviesDao.deleteMovie(result)

    fun getLibraryMovies() = moviesDao.getLibraryMovies()

    suspend fun deleteAllMovies() = moviesDao.deleteAllMovies()
}
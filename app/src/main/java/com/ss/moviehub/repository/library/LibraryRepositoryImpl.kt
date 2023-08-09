package com.ss.moviehub.repository.library

import com.ss.moviehub.data.database.MovieDao
import com.ss.moviehub.data.models.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val moviesDao: MovieDao
) : LibraryRepository {

    override fun getLibraryMovies(): Flow<List<Result>> = moviesDao.getAllMovies()

    override suspend fun addMovie(result: Result) = moviesDao.addAndUpdateMovie(result)

    override suspend fun removeMovie(result: Result) = moviesDao.deleteMovie(result)

    override suspend fun deleteAllMovies() = moviesDao.deleteAllMovies()
}
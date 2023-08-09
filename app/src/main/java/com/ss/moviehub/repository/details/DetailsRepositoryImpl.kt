package com.ss.moviehub.repository.details

import com.ss.moviehub.data.database.MovieDao
import com.ss.moviehub.data.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
) : DetailsRepository {

    override fun isInLibrary(movieId: Int?) = flow {
        movieDao.getAllMovies().collect { movieList ->
            val isOnLibrary = movieList.find { it.id == movieId }
            if (isOnLibrary != null) this.emit(true)
            else this.emit(false)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addMovieToLibrary(movie: Result?) {
        if (movie == null) return
        movieDao.addAndUpdateMovie(movie)
    }

    override suspend fun removeMovieFromLibrary(movie: Result?) {
        if (movie == null) return
        movieDao.deleteMovie(movie)
    }
}
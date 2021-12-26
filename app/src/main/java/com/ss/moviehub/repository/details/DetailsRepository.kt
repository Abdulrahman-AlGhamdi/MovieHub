package com.ss.moviehub.repository.details

import com.ss.moviehub.data.database.MovieDao
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.repository.details.DetailsRepository.DetailsStatus.CheckResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val movieDao: MovieDao
) {

    fun checkIsOnLibrary(movieId: Int) = flow {
        movieDao.getAllMovies().collect { movieList ->
            val isOnLibrary = movieList.find { it.id == movieId }
            if (isOnLibrary != null) this.emit(CheckResult(isOnLibrary = true))
            else this.emit(CheckResult(isOnLibrary = false))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addMovie(result: Result) = movieDao.addAndUpdateMovie(result)

    suspend fun removeMovie(result: Result) = movieDao.deleteMovie(result)

    sealed class DetailsStatus {
        object Idle                                      : DetailsStatus()
        data class CheckResult(val isOnLibrary: Boolean) : DetailsStatus()
    }
}
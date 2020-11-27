package com.ss.moviehub.Repository

import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Database.MovieDatabase
import com.ss.moviehub.Models.Result
import com.ss.moviehub.Utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class   MovieRepository(var database: MovieDatabase) {

    private val api: MovieAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    suspend fun getPopularMovie(pageNumber: Int) =
        api.getPopularMovie(pageNumber)

    suspend fun getTopRatedMovie(pageNumber: Int) =
        api.getTopRatedMovie(pageNumber)

    suspend fun getUpcomingMovie(pageNumber: Int) =
        api.getUpcomingMovie(pageNumber)

    suspend fun getSearchedMovie(search: String, pageNumber: Int) =
        api.getSearchedMovie(search, pageNumber)

    suspend fun addMovieToLibrary(result: Result) =
        database.movieDao().addAndUpdateMovie(result)

    suspend fun deleteMovieLibrary(result: Result) =
        database.movieDao().deleteMovie(result)

    fun getLibraryMovies() =
        database.movieDao().getLibraryMovies()

    suspend fun deleteAllMovies() =
        database.movieDao().deleteAllMovies()
}

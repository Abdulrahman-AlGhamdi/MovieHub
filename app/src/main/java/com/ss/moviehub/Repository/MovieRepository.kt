package com.ss.moviehub.Repository

import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {

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

}

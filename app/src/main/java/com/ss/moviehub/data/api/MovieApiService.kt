package com.ss.moviehub.data.api

import com.ss.moviehub.data.models.MovieResponse
import com.ss.moviehub.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun getSearchedMovie(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieResponse>
}
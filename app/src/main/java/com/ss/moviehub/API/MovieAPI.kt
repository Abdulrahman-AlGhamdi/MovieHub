package com.ss.moviehub.API

import com.ss.moviehub.Models.MovieResponse
import com.ss.moviehub.Utils.Constants.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: String = "1"
    ): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: String = "1"
    ): Call<MovieResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: String = "1"
    ): Call<MovieResponse>

    @GET("search/movie")
    fun getSearchedMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Call<MovieResponse>
}
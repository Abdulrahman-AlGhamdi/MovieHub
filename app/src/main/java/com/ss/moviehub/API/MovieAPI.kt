package com.ss.moviehub.API

import com.ss.moviehub.Models.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") apiKey: String = "c549b0b6a42c2b56589e9be69b41897c",
        @Query("page") page: String = "1"
    ): Call<Movie>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") apiKey: String = "c549b0b6a42c2b56589e9be69b41897c",
        @Query("page") page: String = "1"
    ): Call<Movie>

    @GET("movie/upcoming")
    fun getUpcomingMovie(
        @Query("api_key") apiKey: String = "c549b0b6a42c2b56589e9be69b41897c",
        @Query("page") page: String = "1"
    ): Call<Movie>

    @GET("search/movie")
    fun getSearchedMovie(
        @Query("api_key") apiKey: String = "c549b0b6a42c2b56589e9be69b41897c",
        @Query("query") query: String = ""
    ): Call<Movie>
}
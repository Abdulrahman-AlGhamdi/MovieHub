package com.ss.moviehub.Repository

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Adapters.RecyclerAdapter
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    val api: MovieAPI = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    suspend fun getPopularMovie() {
        val popularResponse = api.getPopularMovie().awaitResponse()
        if (popularResponse.isSuccessful) {
            for (popularMovie in popularResponse.body()?.results!!)
                addToList(
                    "popular",
                    popularMovie.poster_path,
                    popularMovie.title,
                    popularMovie.backdrop_path,
                    popularMovie.release_date,
                    popularMovie.overview,
                    popularMovie.vote_average
                )
        }
    }

    suspend fun getTopRatedMovie() {
        val topRatedResponse = api.getTopRatedMovie().awaitResponse()

        if (topRatedResponse.isSuccessful) {
            for (topRatedMovie in topRatedResponse.body()?.results!!)
                addToList(
                    "topRated",
                    topRatedMovie.poster_path,
                    topRatedMovie.title,
                    topRatedMovie.backdrop_path,
                    topRatedMovie.release_date,
                    topRatedMovie.overview,
                    topRatedMovie.vote_average
                )
        }
    }

    suspend fun getUpcomingMovie() {
        val upcomingResponse = Repository().api.getUpcomingMovie().awaitResponse()

        if (upcomingResponse.isSuccessful) {
            for (upcoming in upcomingResponse.body()?.results!!) {
                Repository().addToList(
                    "upcoming",
                    upcoming.poster_path,
                    upcoming.title,
                    upcoming.backdrop_path,
                    upcoming.release_date,
                    upcoming.overview,
                    upcoming.vote_average
                )
            }
        }
    }

    suspend fun getSearchedMovie(search: String) {
        val response = Repository()
            .api.getSearchedMovie("c549b0b6a42c2b56589e9be69b41897c", search)
            .awaitResponse()

        if (response.isSuccessful) {
            for (searchedMovie in response.body()?.results!!) {
                addToList(
                    "search",
                    searchedMovie.poster_path,
                    searchedMovie.title,
                    searchedMovie.backdrop_path,
                    searchedMovie.release_date,
                    searchedMovie.overview,
                    searchedMovie.vote_average
                )
            }
        }
    }

    private fun addToList(
        category: String,
        poster: String,
        title: String,
        backdrop: String,
        releaseDate: String,
        overview: String,
        voteAverage: Double
    ) {
        when (category) {
            "popular" -> {
                popularPosterList.add(poster)
                popularTitleList.add(title)
                popularBackdropList.add(backdrop)
                popularReleaseDateList.add(releaseDate)
                popularOverviewList.add(overview)
                popularVoteAverageList.add(voteAverage)
            }
            "topRated" -> {
                topRatedPosterList.add(poster)
                topRatedTitleLists.add(title)
                topRatedBackdropLists.add(backdrop)
                topRatedReleaseDateLists.add(releaseDate)
                topRatedOverviewLists.add(overview)
                topRatedVoteAverageLists.add(voteAverage)
            }
            "upcoming" -> {
                upcomingPosterList.add(poster)
                upcomingTitleLists.add(title)
                upcomingBackdropLists.add(backdrop)
                upcomingReleaseDateLists.add(releaseDate)
                upcomingOverviewLists.add(overview)
                upcomingVoteAverageLists.add(voteAverage)
            }
            "search" -> {
                searchPosterList.add(poster)
                searchTitleLists.add(title)
                searchBackdropLists.add(backdrop)
                searchReleaseDateLists.add(releaseDate)
                searchOverviewLists.add(overview)
                searchVoteAverageLists.add(voteAverage)
            }
        }
    }

    fun setupRecyclerView(recyclerView: RecyclerView) {
        when (recyclerView.id) {
            R.id.result_movie -> recyclerView.layoutManager =
                GridLayoutManager(recyclerView.context, 3)
            R.id.popular_movies -> recyclerView.layoutManager =
                LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            R.id.top_rated_movies -> recyclerView.layoutManager =
                LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            R.id.upcoming_movies -> recyclerView.layoutManager =
                LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        }

        val popularMovie = RecyclerAdapter(
            popularPosterList,
            popularTitleList,
            popularBackdropList,
            popularReleaseDateList,
            popularOverviewList,
            popularVoteAverageList
        )

        val topRatedMovie = RecyclerAdapter(
            topRatedPosterList,
            topRatedTitleLists,
            topRatedBackdropLists,
            topRatedReleaseDateLists,
            topRatedOverviewLists,
            topRatedVoteAverageLists
        )

        val upcomingMovie = RecyclerAdapter(
            upcomingPosterList,
            upcomingTitleLists,
            upcomingBackdropLists,
            upcomingReleaseDateLists,
            upcomingOverviewLists,
            upcomingVoteAverageLists
        )

        val searchMovie = RecyclerAdapter(
            searchPosterList,
            searchTitleLists,
            searchBackdropLists,
            searchReleaseDateLists,
            searchOverviewLists,
            searchVoteAverageLists
        )

        when (recyclerView.id) {
            R.id.popular_movies -> recyclerView.adapter = popularMovie
            R.id.top_rated_movies -> recyclerView.adapter = topRatedMovie
            R.id.upcoming_movies -> recyclerView.adapter = upcomingMovie
            R.id.result_movie -> recyclerView.adapter = searchMovie
        }
    }
}
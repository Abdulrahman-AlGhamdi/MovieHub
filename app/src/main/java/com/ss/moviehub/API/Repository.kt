package com.ss.moviehub.API

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.RecyclerAdapter

class Repository {

    fun addToList(
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
                posterList.add(poster)
                titleList.add(title)
                backdropList.add(backdrop)
                releaseDateList.add(releaseDate)
                overviewList.add(overview)
                voteAverageList.add(voteAverage)
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
            posterList,
            titleList,
            backdropList,
            releaseDateList,
            overviewList,
            voteAverageList
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

        when (recyclerView.id) {
            R.id.popular_movies -> recyclerView.adapter = popularMovie
            R.id.top_rated_movies -> recyclerView.adapter = topRatedMovie
            R.id.upcoming_movies -> recyclerView.adapter = upcomingMovie
            R.id.result_movie -> recyclerView.adapter = popularMovie
        }
    }
}
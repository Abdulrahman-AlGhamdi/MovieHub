package com.ss.moviehub.API

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.*
import com.ss.moviehub.Adapters.RecyclerAdapter

class Repository {

    fun addToList(
        poster: String,
        title: String,
        backdrop: String,
        releaseDate: String,
        overview: String,
        voteAverage: Double
    ) {
        posterList.add(poster)
        titleList.add(title)
        backdropList.add(backdrop)
        releaseDateList.add(releaseDate)
        overviewList.add(overview)
        voteAverageList.add(voteAverage)
    }

    fun addToTopRatedList(
        poster: String,
        title: String,
        backdrop: String,
        releaseDate: String,
        overview: String,
        voteAverage: Double
    ) {
        posterLists.add(poster)
        titleLists.add(title)
        backdropLists.add(backdrop)
        releaseDateLists.add(releaseDate)
        overviewLists.add(overview)
        voteAverageLists.add(voteAverage)
    }

    fun setupRecyclerView(recyclerView: RecyclerView) {
        when (recyclerView.id) {
            R.id.result_movie -> recyclerView.layoutManager =
                GridLayoutManager(recyclerView.context, 3)
            R.id.popular_movies -> recyclerView.layoutManager =
                LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            R.id.top_rated_movies -> recyclerView.layoutManager =
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
            posterLists,
            titleLists,
            backdropLists,
            releaseDateLists,
            overviewLists,
            voteAverageLists
        )

        when(recyclerView.id){
            R.id.popular_movies -> recyclerView.adapter = popularMovie
            R.id.top_rated_movies -> recyclerView.adapter = topRatedMovie
            R.id.result_movie -> recyclerView.adapter = popularMovie
        }
    }
}
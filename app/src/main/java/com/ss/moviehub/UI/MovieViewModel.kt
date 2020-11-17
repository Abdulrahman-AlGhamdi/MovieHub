package com.ss.moviehub.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ss.moviehub.Models.Result
import com.ss.moviehub.Repository.Repository

class MovieViewModel : ViewModel() {
    val popularMoviesLiveData: LiveData<List<Result>> = Repository().getPopularMovie()
    val topRatedMoviesLiveData: LiveData<List<Result>> = Repository().getTopRatedMovie()
    val upcomingMoviesLiveData: LiveData<List<Result>> = Repository().getUpcomingMovie()
    lateinit var searchedMoviesLiveData: LiveData<List<Result>>
}
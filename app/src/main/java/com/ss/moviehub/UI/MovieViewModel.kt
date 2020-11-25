package com.ss.moviehub.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.Models.Result
import com.ss.moviehub.Repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    var popularMoviePage = 1
    var topRatedMoviePage = 1
    var upcomingMoviePage = 1
    var searchMoviePage = 1
    private var movieRepository = MovieRepository()
    val popularMoviesLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    val topRatedMoviesLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    val upcomingMoviesLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    val searchedMoviesLiveData: MutableLiveData<List<Result>> = MutableLiveData()

    init {
        getPopularMovie()
        getTopRatedMovie()
        getUpcomingMovie()
    }

    private fun getPopularMovie() = viewModelScope.launch {
        val response = movieRepository.getPopularMovie(popularMoviePage)
        if (response.isSuccessful)
            popularMoviesLiveData.postValue(response.body()?.results)
    }

    private fun getTopRatedMovie() = viewModelScope.launch {
        val response = movieRepository.getTopRatedMovie(topRatedMoviePage)
        if (response.isSuccessful)
            topRatedMoviesLiveData.postValue(response.body()?.results)
    }

    private fun getUpcomingMovie() = viewModelScope.launch {
        val response = movieRepository.getUpcomingMovie(upcomingMoviePage)
        if (response.isSuccessful)
            upcomingMoviesLiveData.postValue(response.body()?.results)
    }

    fun getSearchMovie(search: String) = viewModelScope.launch {
        val response = movieRepository.getSearchedMovie(search, upcomingMoviePage)
        if (response.isSuccessful)
            searchedMoviesLiveData.postValue(response.body()?.results)
    }
}
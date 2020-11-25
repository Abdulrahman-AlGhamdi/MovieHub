package com.ss.moviehub.UI.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.moviehub.Models.Result
import com.ss.moviehub.Repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private var repository: MovieRepository) : ViewModel() {

    var popularMoviePage = 1
    var topRatedMoviePage = 1
    var upcomingMoviePage = 1
    var searchMoviePage = 1

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
        val response = repository.getPopularMovie(popularMoviePage)
        if (response.isSuccessful)
            popularMoviesLiveData.postValue(response.body()?.results)
    }

    private fun getTopRatedMovie() = viewModelScope.launch {
        val response = repository.getTopRatedMovie(topRatedMoviePage)
        if (response.isSuccessful)
            topRatedMoviesLiveData.postValue(response.body()?.results)
    }

    private fun getUpcomingMovie() = viewModelScope.launch {
        val response = repository.getUpcomingMovie(upcomingMoviePage)
        if (response.isSuccessful)
            upcomingMoviesLiveData.postValue(response.body()?.results)
    }

    fun getSearchMovie(search: String) = viewModelScope.launch {
        val response = repository.getSearchedMovie(search, upcomingMoviePage)
        if (response.isSuccessful)
            searchedMoviesLiveData.postValue(response.body()?.results)
    }

    fun addMovieToLibrary(result: Result) = viewModelScope.launch {
        repository.addMovieToLibrary(result)
    }

    fun deleteMovieFromLibrary(result: Result) = viewModelScope.launch {
        repository.deleteMovieLibrary(result)
    }

    fun getLibraryMovies() = repository.getLibraryMovies()
}
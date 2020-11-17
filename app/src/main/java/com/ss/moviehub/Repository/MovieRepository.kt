package com.ss.moviehub.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Models.MovieResponse
import com.ss.moviehub.Models.Result
import com.ss.moviehub.Utils.Constants.BASE_URL
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    private val api: MovieAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieAPI::class.java)

    fun getPopularMovie(): LiveData<List<Result>> {

        val responseLiveData: MutableLiveData<List<Result>> = MutableLiveData()
        val popularResponse = api.getPopularMovie()

        popularResponse.enqueue(object : Callback<MovieResponse> {

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                responseLiveData.value = response.body()?.results
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }
        })

        return responseLiveData
    }

    fun getTopRatedMovie(): LiveData<List<Result>> {

        val responseLiveData: MutableLiveData<List<Result>> = MutableLiveData()
        val topRatedResponse = api.getTopRatedMovie()

        topRatedResponse.enqueue(object : Callback<MovieResponse> {

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                responseLiveData.value = response.body()?.results
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }
        })

        return responseLiveData
    }

    fun getUpcomingMovie(): LiveData<List<Result>> {

        val responseLiveData: MutableLiveData<List<Result>> = MutableLiveData()
        val upcomingResponse = api.getUpcomingMovie()

        upcomingResponse.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                responseLiveData.value = response.body()?.results
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }
        })

        return responseLiveData
    }

    fun getSearchedMovie(search: String): LiveData<List<Result>> {

        val responseLiveData: MutableLiveData<List<Result>> = MutableLiveData()
        val searchResponse = Repository()
            .api.getSearchedMovie("c549b0b6a42c2b56589e9be69b41897c", search)

        searchResponse.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                responseLiveData.value = response.body()?.results
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }
        })

        return responseLiveData
    }
}

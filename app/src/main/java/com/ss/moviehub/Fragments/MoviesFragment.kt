package com.ss.moviehub.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Adapters.RecyclerAdapter
import com.ss.moviehub.R
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MoviesFragment : Fragment() {

    private var posterList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        popularMovieRequest()

        return view
    }

    private fun popularMovieRequest(){
        val api = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getPopularMovie().awaitResponse()

                if (response.isSuccessful){
                    for(movie in response.body()?.results!!){
                        addToList(movie.poster_path)
                    }
                    withContext(Dispatchers.Main){
                        setupRecyclerView()
                    }
                }

            } catch (e: Exception){
                Log.d("MovieResult", e.toString())
            }
        }
    }

    private fun addToList(poster: String){
        posterList.add(poster)
    }

    private fun setupRecyclerView(){
        popular_movies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        popular_movies.adapter = RecyclerAdapter(posterList)
    }
}
package com.ss.moviehub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Adapter.RecyclerAdapter
import com.ss.moviehub.Model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class MainActivity : AppCompatActivity() {

    lateinit var countDownTimer: CountDownTimer
    private var posterList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularMovieRequest()
    }

    private fun setupRecyclerView(){
        popular_movies.layoutManager = GridLayoutManager(this, 3)
        popular_movies.adapter = RecyclerAdapter(posterList)
    }

    private fun addToList(poster: String){
        posterList.add(poster)
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
}
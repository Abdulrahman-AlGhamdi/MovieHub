package com.ss.moviehub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ss.moviehub.API.MovieAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getMovie().awaitResponse()
            if (response.isSuccessful) {
                val responseData = response.body()!!
                Log.d("MovieResult", responseData.title)
                Log.d("MovieResult", responseData.status)
            }
        }
    }
}
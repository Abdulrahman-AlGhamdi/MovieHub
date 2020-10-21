package com.ss.moviehub

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.ss.moviehub.API.MovieAPI
import com.ss.moviehub.Adapters.RecyclerAdapter
import com.ss.moviehub.Fragments.MoviesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var moviesFragment: Fragment = MoviesFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, moviesFragment)
            .commit()
    }
}
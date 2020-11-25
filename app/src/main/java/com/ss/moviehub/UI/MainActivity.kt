package com.ss.moviehub.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ss.moviehub.Database.MovieDatabase
import com.ss.moviehub.R
import com.ss.moviehub.Repository.MovieRepository
import com.ss.moviehub.UI.ViewModel.MovieViewModel
import com.ss.moviehub.UI.ViewModel.MovieViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = MovieRepository(MovieDatabase(this))
        val viewModelProviderFactory = MovieViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MovieViewModel::class.java)

        navigation_bar.setupWithNavController(findNavController(R.id.fragment))
    }
}
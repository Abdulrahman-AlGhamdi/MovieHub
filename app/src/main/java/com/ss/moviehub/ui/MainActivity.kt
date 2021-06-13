package com.ss.moviehub.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ss.moviehub.R
import com.ss.moviehub.databinding.ActivityMainBinding
import com.ss.universitiesdirectory.utils.LanguageHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment_container)
        val configuration = AppBarConfiguration(setOf(R.id.moviesFragment, R.id.searchFragment, R.id.libraryFragment))
        setupActionBarWithNavController(navController, configuration)
        binding.navigationBar.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.onBaseAttach(newBase))
    }
}
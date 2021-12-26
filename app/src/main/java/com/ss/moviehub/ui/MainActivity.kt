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
import com.ss.moviehub.utils.LanguageHelper
import com.ss.moviehub.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment_container)
        val configuration = AppBarConfiguration(setOf(R.id.moviesFragment, R.id.searchFragment, R.id.libraryFragment))
        setupActionBarWithNavController(navController, configuration)
        binding.navigationBar.setupWithNavController(navController)
        binding.navigationBar.setOnItemReselectedListener {  }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.onBaseAttach(newBase))
    }
}
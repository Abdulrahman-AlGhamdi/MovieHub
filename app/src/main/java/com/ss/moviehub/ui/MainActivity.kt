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
import com.ss.moviehub.utils.viewBinding
import com.ss.universitiesdirectory.utils.LanguageHelper
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.moviesFragment -> {
                    binding.navigationBar.menu.getItem(0).isEnabled = false
                    binding.navigationBar.menu.getItem(1).isEnabled = true
                    binding.navigationBar.menu.getItem(2).isEnabled = true
                }
                R.id.searchFragment -> {
                    binding.navigationBar.menu.getItem(0).isEnabled = true
                    binding.navigationBar.menu.getItem(1).isEnabled = false
                    binding.navigationBar.menu.getItem(2).isEnabled = true
                }
                R.id.libraryFragment -> {
                    binding.navigationBar.menu.getItem(0).isEnabled = true
                    binding.navigationBar.menu.getItem(1).isEnabled = true
                    binding.navigationBar.menu.getItem(2).isEnabled = false
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.onBaseAttach(newBase))
    }
}
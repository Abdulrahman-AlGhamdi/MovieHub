package com.ss.moviehub.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ss.moviehub.ui.bnb.BottomNavigationBar
import com.ss.moviehub.ui.navigation.Navigation
import com.ss.moviehub.ui.theme.MovieHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent(content = { App() })
    }

    @Composable
    @OptIn(ExperimentalAnimationApi::class)
    private fun App(): Unit = MovieHubTheme {
        navController = rememberAnimatedNavController()

        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) },
            content = { Navigation(navController = navController, paddingValues = it) }
        )
    }
}
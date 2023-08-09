package com.ss.moviehub.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.screen.details.DetailsScreen
import com.ss.moviehub.screen.details.DetailsViewModel
import com.ss.moviehub.screen.library.LibraryScreen
import com.ss.moviehub.screen.library.LibraryViewModel
import com.ss.moviehub.screen.movies.MoviesScreen
import com.ss.moviehub.screen.movies.MoviesViewModel
import com.ss.moviehub.screen.search.SearchScreen
import com.ss.moviehub.screen.search.SearchViewModel
import com.ss.moviehub.screen.settings.SettingsScreen
import com.ss.moviehub.screen.settings.SettingsViewModel

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun Navigation(navController: NavHostController, paddingValues: PaddingValues) = AnimatedNavHost(
    navController = navController,
    startDestination = Screen.MoviesScreen.route,
    enterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None },
    popEnterTransition = { EnterTransition.None },
    popExitTransition = { ExitTransition.None },
    modifier = Modifier.padding(paddingValues)
) {
    composable(route = Screen.MoviesScreen.route, content = {
        val viewModel = hiltViewModel<MoviesViewModel>()
        val popularMovies by viewModel.popularMovies.collectAsState()
        val topRatedMovies by viewModel.topRatedMovies.collectAsState()
        val upcomingMovies by viewModel.upcomingMovies.collectAsState()

        MoviesScreen(
            popularMovies = popularMovies,
            topRatedMovies = topRatedMovies,
            upcomingMovies = upcomingMovies,
            onMovieClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("movie", it)
                navController.navigate(route = Screen.DetailsScreen.route)
            }
        )
    })
    composable(route = Screen.SearchScreen.route, content = {
        val viewModel = hiltViewModel<SearchViewModel>()
        val searchedMovies by viewModel.searchedMovies.collectAsState()

        SearchScreen(
            searchedMovies = searchedMovies,
            onSearchClick = { viewModel.getSearchMovies(it); viewModel.saveLastSearch(it) },
            onMovieClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("movie", it)
                navController.navigate(route = Screen.DetailsScreen.route)
            },
            getLastSearch = viewModel::getLastSearch
        )
    })
    composable(route = Screen.LibraryScreen.route, content = {
        val viewModel = hiltViewModel<LibraryViewModel>()
        val librariesMovies by viewModel.libraryList.collectAsState()

        LibraryScreen(
            librariesMovies = librariesMovies,
            onAddClick = {
                navController.popBackStack()
                navController.navigate(route = Screen.MoviesScreen.route)
            },
            onMovieClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("movie", it)
                navController.navigate(route = Screen.DetailsScreen.route)
            },
            onDeleteAllCLick = viewModel::deleteAllMovies
        )
    })
    composable(route = Screen.DetailsScreen.route, content = {
        val viewModel = hiltViewModel<DetailsViewModel>()
        val isInLibrary by viewModel.isInLibrary.collectAsState()
        val previousBackStackEntry = navController.previousBackStackEntry?.savedStateHandle
        val movie = previousBackStackEntry?.get<Result>("movie")

        DetailsScreen(
            movie = movie,
            isInLibrary = isInLibrary,
            onAddClick = { viewModel.addMovieToLibrary(movie) },
            onBackClick = navController::popBackStack,
            onRemoveClick = { viewModel.removeMovie(movie) },
            checkIfAddedToLibrary = { viewModel.checkIsInLibrary(movie?.id) }
        )
    })
    composable(route = Screen.SettingsScreen.route, content = {
        val viewModel = hiltViewModel<SettingsViewModel>()

        SettingsScreen(onContactClick = viewModel::openTwitter)
    })
}

sealed class Screen(val route: String) {
    object MoviesScreen : Screen(route = "movies")
    object SearchScreen : Screen(route = "search")
    object LibraryScreen : Screen(route = "library")
    object DetailsScreen : Screen(route = "details")
    object SettingsScreen : Screen(route = "settings")

    fun withParams(vararg args: String): String = buildString {
        append(route)
        args.forEach { append("/{$it}") }
    }

    fun <T> withArgs(vararg params: T): String = buildString {
        append(route)
        params.forEach { append("/$it") }
    }
}
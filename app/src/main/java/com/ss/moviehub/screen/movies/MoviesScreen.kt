package com.ss.moviehub.screen.movies

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ss.moviehub.R
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.repository.movies.MoviesRepository.ResponseStatus
import com.ss.moviehub.repository.movies.MoviesRepository.ResponseStatus.Failed
import com.ss.moviehub.repository.movies.MoviesRepository.ResponseStatus.Successful
import com.ss.moviehub.screen.movies.component.MoviesList
import com.ss.moviehub.ui.common.DefaultCenterTopAppBar
import com.ss.moviehub.ui.common.DefaultIcon
import com.ss.moviehub.ui.common.DefaultLinearProgressIndicator
import com.ss.moviehub.ui.common.DefaultText
import com.ss.moviehub.ui.theme.MovieHubTheme

@Composable
fun MoviesScreen(
    popularMovies: ResponseStatus,
    topRatedMovies: ResponseStatus,
    upcomingMovies: ResponseStatus,
    onMovieClick: (Result) -> Unit
) = Scaffold(topBar = { MoviesTopAppBar() }) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(it)
            .verticalScroll(scrollState)
            .padding(PaddingValues(bottom = 8.dp))
    ) {
        PopularMovies(popularMovies = popularMovies, onMovieClick = onMovieClick)
        TopRatedMovies(topRatedMovies = topRatedMovies, onMovieClick = onMovieClick)
        UpcomingMovies(upcomingMovies = upcomingMovies, onMovieClick = onMovieClick)
    }
}

@Composable
private fun MoviesTopAppBar() = DefaultCenterTopAppBar(title = R.string.movies_screen)

@Composable
private fun PopularMovies(popularMovies: ResponseStatus, onMovieClick: (Result) -> Unit) {
    when (popularMovies) {
        is Successful -> MoviesList(
            title = R.string.movies_popular_title,
            text = R.string.movies_popular_text,
            list = popularMovies.movieList,
            onMovieClick = onMovieClick
        )

        ResponseStatus.Loading -> DefaultLinearProgressIndicator()
        is Failed -> Unit
        else -> Unit
    }
}

@Composable
private fun TopRatedMovies(topRatedMovies: ResponseStatus, onMovieClick: (Result) -> Unit) {
    when (topRatedMovies) {
        is Successful -> MoviesList(
            title = R.string.movies_top_rated_title,
            text = R.string.movies_top_rated_text,
            list = topRatedMovies.movieList,
            onMovieClick = onMovieClick
        )

        is Failed -> Unit
        ResponseStatus.Idle -> Unit
        ResponseStatus.Loading -> DefaultLinearProgressIndicator()
    }
}

@Composable
private fun UpcomingMovies(upcomingMovies: ResponseStatus, onMovieClick: (Result) -> Unit) {
    when (upcomingMovies) {
        is Successful -> MoviesList(
            title = R.string.movies_upcoming_title,
            text = R.string.movies_upcoming_text,
            list = upcomingMovies.movieList,
            onMovieClick = onMovieClick
        )

        is Failed -> Unit
        ResponseStatus.Idle -> Unit
        ResponseStatus.Loading -> DefaultLinearProgressIndicator()
    }
}

@Composable
private fun NoInternet() = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    DefaultIcon(imageVector = Icons.Default.WifiOff, modifier = Modifier.size(50.dp))
    DefaultText(text = R.string.no_internet, modifier = Modifier.padding(top = 16.dp))
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "AR")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "EN")
private fun MoviesScreenPreview() = MovieHubTheme {
    MoviesScreen(
        popularMovies = ResponseStatus.Idle,
        topRatedMovies = ResponseStatus.Idle,
        upcomingMovies = ResponseStatus.Idle,
        onMovieClick = {}
    )
}
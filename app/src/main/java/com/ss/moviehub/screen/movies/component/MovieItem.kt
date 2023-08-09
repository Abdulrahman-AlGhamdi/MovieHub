package com.ss.moviehub.screen.movies.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ss.moviehub.data.models.Result

@Composable
fun MovieItem(movie: Result, onMovieClick: (Result) -> Unit) = AsyncImage(
    modifier = Modifier.clickable(onClick = { onMovieClick(movie) }),
    model = "https://image.tmdb.org/t/p/w342${movie.posterPath}",
    contentDescription = null,
)
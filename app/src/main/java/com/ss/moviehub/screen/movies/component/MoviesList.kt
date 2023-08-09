package com.ss.moviehub.screen.movies.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.ui.common.DefaultText

@Composable
fun MoviesList(title: Int, text: Int, list: List<Result>, onMovieClick: (Result) -> Unit) {
    ListItem(
        headlineContent = { DefaultText(text = title) },
        supportingContent = { DefaultText(text = text) }
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        content = { items(items = list) { MovieItem(movie = it, onMovieClick = onMovieClick) } }
    )
}
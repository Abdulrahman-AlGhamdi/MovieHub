package com.ss.moviehub.screen.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import com.ss.moviehub.R
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.ui.common.DefaultButton
import com.ss.moviehub.ui.common.DefaultCenterTopAppBar
import com.ss.moviehub.ui.common.DefaultIcon
import com.ss.moviehub.ui.common.DefaultIconButton
import com.ss.moviehub.ui.common.DefaultScaffold
import com.ss.moviehub.ui.theme.MovieHubTheme
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun DetailsScreen(
    movie: Result?,
    isInLibrary: Boolean,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit,
    onRemoveClick: () -> Unit,
    checkIfAddedToLibrary: () -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) = DefaultScaffold(topBar = { DetailsTopAppBar(onBackClick = onBackClick) }) {
    MovieBackdrop(movie = movie)
    MovieDetails(movie = movie)
    MovieOverview(movie = movie)
    Spacer(modifier = Modifier.weight(1f))

    AddToOrRemoveFromLibrary(
        isInLibrary = isInLibrary,
        onAddClick = onAddClick,
        onRemoveClick = onRemoveClick
    )

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) checkIfAddedToLibrary()
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    })
}

@Composable
private fun DetailsTopAppBar(onBackClick: () -> Unit) = DefaultCenterTopAppBar(
    title = R.string.details_screen,
    navigationIcon = { DefaultIconButton(onClick = onBackClick, icon = Icons.Default.ArrowBack) }
)

@Composable
private fun MovieBackdrop(movie: Result?) = AsyncImage(
    modifier = Modifier.fillMaxWidth(),
    model = "https://image.tmdb.org/t/p/w342${movie?.backdropPath}",
    contentDescription = null
)

@Composable
private fun MovieDetails(movie: Result?) = Row(verticalAlignment = Alignment.CenterVertically) {
    AsyncImage(
        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
        model = "https://image.tmdb.org/t/p/w342${movie?.posterPath}",
        contentDescription = null
    )

    Column {
        ListItem(
            headlineContent = { Text(text = movie?.title ?: "") },
            supportingContent = { Text(text = movie?.releaseDate ?: "") }
        )

        RatingBar(
            rating = ((movie?.voteAverage ?: 0.0) / 2),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) = Row(modifier = modifier) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    repeat(filledStars) {
        DefaultIcon(imageVector = Icons.Outlined.Star, tint = starsColor)
    }

    if (halfStar) Icon(
        imageVector = Icons.Outlined.StarHalf,
        contentDescription = null,
        tint = starsColor
    )

    repeat(unfilledStars) {
        DefaultIcon(imageVector = Icons.Outlined.StarOutline, tint = starsColor)
    }
}

@Composable
private fun MovieOverview(movie: Result?) = Text(
    text = movie?.overview ?: "",
    textAlign = TextAlign.Justify,
    modifier = Modifier.padding(16.dp)
)

@Composable
private fun AddToOrRemoveFromLibrary(
    isInLibrary: Boolean,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit
) = if (isInLibrary) DefaultButton(
    onClick = onRemoveClick,
    modifier = Modifier.padding(16.dp),
    text = R.string.details_remove_movie
) else DefaultButton(
    onClick = onAddClick,
    modifier = Modifier.padding(16.dp),
    text = R.string.details_add_movie
)

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "AR")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "EN")
private fun DetailsScreenPreview() = MovieHubTheme {
    DetailsScreen(
        movie = null,
        isInLibrary = false,
        onAddClick = {},
        onBackClick = {},
        onRemoveClick = {},
        checkIfAddedToLibrary = {}
    )
}
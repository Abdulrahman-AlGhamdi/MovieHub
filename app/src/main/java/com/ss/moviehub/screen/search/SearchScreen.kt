package com.ss.moviehub.screen.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ss.moviehub.R
import com.ss.moviehub.data.models.Result
import com.ss.moviehub.repository.search.SearchRepository.*
import com.ss.moviehub.screen.movies.component.MovieItem
import com.ss.moviehub.ui.common.DefaultCenterTopAppBar
import com.ss.moviehub.ui.common.DefaultIconButton
import com.ss.moviehub.ui.common.DefaultLinearProgressIndicator
import com.ss.moviehub.ui.common.DefaultScaffold
import com.ss.moviehub.ui.common.DefaultText
import com.ss.moviehub.ui.theme.MovieHubTheme

@Composable
fun SearchScreen(
    searchedMovies: ResponseStatus,
    onSearchClick: (String) -> Unit,
    onMovieClick: (Result) -> Unit,
    getLastSearch: () -> String
) {
    val snackBarHost = remember { SnackbarHostState() }
    var searchText by rememberSaveable { mutableStateOf("") }
    var isSearching by rememberSaveable { mutableStateOf(false) }

    DefaultScaffold(
        topBar = {
            if (isSearching) SearchTopAppBar(
                searchText = searchText,
                onSearchTextChange = { searchText = it },
                onSearchClick = onSearchClick,
                onClose = { isSearching = false; searchText = "" }
            ) else DefaultSearchTopAppBar(onSearchClick = { isSearching = it })
        },
        snackBarHost = { SnackbarHost(hostState = snackBarHost) }
    ) {
        LastSearch(getLastSearch = getLastSearch)

        when (searchedMovies) {
            is ResponseStatus.Successful -> SearchList(
                list = searchedMovies.movieList,
                onMovieClick = onMovieClick
            )

            ResponseStatus.Loading -> DefaultLinearProgressIndicator()
            is ResponseStatus.Failed -> Unit
            else -> Unit
        }
    }
}

@Composable
private fun DefaultSearchTopAppBar(onSearchClick: (Boolean) -> Unit) = DefaultCenterTopAppBar(
    actions = {
        DefaultIconButton(onClick = { onSearchClick(true) }, icon = Icons.Default.Search)
    },
    title = R.string.search_screen
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchTopAppBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onClose: () -> Unit
) = TopAppBar(
    title = {
        val focusRequester = remember { FocusRequester() }

        TextField(
            singleLine = true,
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            placeholder = { DefaultText(text = R.string.search_screen) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearchClick(searchText) }),
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(0.dp)
        )

        LaunchedEffect(key1 = focusRequester) { focusRequester.requestFocus() }
    },
    navigationIcon = { DefaultIconButton(onClick = {}, icon = Icons.Default.Search) },
    actions = { DefaultIconButton(onClick = onClose, icon = Icons.Default.Close) },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.background)
)

@Composable
private fun LastSearch(getLastSearch: () -> String) = ListItem(
    headlineContent = { Text(text = stringResource(id = R.string.search_last, getLastSearch())) },
)

@Composable
private fun SearchList(list: List<Result>, onMovieClick: (Result) -> Unit) = LazyVerticalGrid(
    columns = GridCells.Fixed(3),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
    content = { items(items = list) { MovieItem(movie = it, onMovieClick = onMovieClick) } }
)

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "AR")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "EN")
private fun SearchScreenPreview() = MovieHubTheme {
    SearchScreen(
        searchedMovies = ResponseStatus.Idle,
        onSearchClick = {},
        onMovieClick = {},
        getLastSearch = { "" }
    )
}
package com.ss.moviehub.ui.bnb

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.LocalLibrary
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ss.moviehub.R
import com.ss.moviehub.ui.common.DefaultIcon
import com.ss.moviehub.ui.common.DefaultText
import com.ss.moviehub.ui.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val systemUiController = rememberSystemUiController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val isFound = screensShowBNB.find { it == currentRoute } != null

    if (isFound) NavigationBar {
        bottomNavigationScreenList.forEach { item ->
            val isSelected = item.screen.route == currentRoute
            val icon = if (isSelected) item.selectedItem else item.defaultIcon

            NavigationBarItem(
                label = { DefaultText(text = item.name) },
                icon = { DefaultIcon(imageVector = icon) },
                onClick = {
                    if (isSelected) return@NavigationBarItem
                    navController.popBackStack()
                    navController.navigate(route = item.screen.route)
                },
                selected = isSelected
            )
        }
    }

    if (isFound) systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        darkIcons = !isSystemInDarkTheme()
    ) else systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.background,
        darkIcons = !isSystemInDarkTheme()
    )
}

val bottomNavigationScreenList = listOf(
    BottomNavigationItem(
        name = R.string.movies_screen,
        screen = Screen.MoviesScreen,
        defaultIcon = Icons.Outlined.Movie,
        selectedItem = Icons.Filled.Movie
    ),
    BottomNavigationItem(
        name = R.string.search_screen,
        screen = Screen.SearchScreen,
        defaultIcon = Icons.Outlined.Search,
        selectedItem = Icons.Filled.Search
    ),
    BottomNavigationItem(
        name = R.string.library_screen,
        screen = Screen.LibraryScreen,
        defaultIcon = Icons.Outlined.LocalLibrary,
        selectedItem = Icons.Filled.LocalLibrary
    ),
    BottomNavigationItem(
        name = R.string.settings_screen,
        screen = Screen.SettingsScreen,
        defaultIcon = Icons.Outlined.Settings,
        selectedItem = Icons.Filled.Settings
    )
)

val screensShowBNB = listOf(
    Screen.MoviesScreen.route,
    Screen.SearchScreen.route,
    Screen.LibraryScreen.route,
    Screen.SettingsScreen.route
)

data class BottomNavigationItem(
    val name: Int,
    val screen: Screen,
    val defaultIcon: ImageVector,
    val selectedItem: ImageVector
)
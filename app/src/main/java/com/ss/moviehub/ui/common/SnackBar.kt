package com.ss.moviehub.ui.common

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun DefaultSnackBar(
    snackBarState: SnackbarHostState,
    message: String
) = LaunchedEffect(key1 = Unit, block = {
    snackBarState.currentSnackbarData?.dismiss()
    snackBarState.showSnackbar(message)
})
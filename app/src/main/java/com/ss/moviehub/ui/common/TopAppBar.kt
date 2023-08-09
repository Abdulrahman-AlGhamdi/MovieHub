package com.ss.moviehub.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.ss.moviehub.ui.common.DefaultText

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DefaultCenterTopAppBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) = CenterAlignedTopAppBar(
    title = { DefaultText(text = title) },
    modifier = modifier,
    navigationIcon = navigationIcon,
    actions = actions
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DefaultCenterTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) = CenterAlignedTopAppBar(
    title = { Text(text = title, overflow = TextOverflow.Ellipsis, maxLines = 1) },
    modifier = modifier,
    navigationIcon = navigationIcon,
    actions = actions
)
package com.ss.moviehub.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Composable
fun DefaultIcon(
    @DrawableRes painter: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current
) = Icon(
    tint = tint,
    modifier = modifier,
    painter = painterResource(id = painter),
    contentDescription = contentDescription
)

@Composable
fun DefaultIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current
) = Icon(
    tint = tint,
    modifier = modifier,
    imageVector = imageVector,
    contentDescription = contentDescription
)
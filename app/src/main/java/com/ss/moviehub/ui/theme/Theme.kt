package com.ss.moviehub.ui.theme

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryColor,
    onPrimary = LightOnPrimaryColor,
    secondary = LightPrimaryColor,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = LightBackgroundColor,
    onBackground = Grey10,
    surface = Color.White,
    surfaceTint = Color.White,
    onSurface = Grey10,
    surfaceVariant = Color.White,
    onSurfaceVariant = ColorOnSurfaceVariantColor,
    outlineVariant = LightOutlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryColor,
    onPrimary = DarkOnPrimaryColor,
    secondary = DarkPrimaryColor,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkBackgroundColor,
    onBackground = Grey90,
    surface = DarkSurfaceColor,
    surfaceTint = DarkSurfaceColor,
    onSurface = Grey80,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariantColor,
    outlineVariant = DarkOutlineVariant
)

@Composable
fun MovieHubTheme(
    context: Context = LocalContext.current,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicTheme: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    val colorScheme = when {
        dynamicTheme && darkTheme -> dynamicDarkColorScheme(context)
        dynamicTheme && !darkTheme -> dynamicLightColorScheme(context)
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    systemUiController.setSystemBarsColor(color = colorScheme.background, darkIcons = !darkTheme)
    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
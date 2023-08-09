package com.ss.moviehub.screen.settings.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.ss.moviehub.ui.common.DefaultIcon
import com.ss.moviehub.ui.common.DefaultText

@Composable
fun SettingsItem(@StringRes title: Int, icon: ImageVector, onClick: () -> Unit) = ListItem(
    headlineContent = { DefaultText(text = title) },
    leadingContent = { DefaultIcon(imageVector = icon) },
    colors = ListItemDefaults.colors(
        containerColor = Color.Unspecified,
        leadingIconColor = MaterialTheme.colorScheme.primary
    ),
    modifier = Modifier.clickable(onClick = onClick)
)
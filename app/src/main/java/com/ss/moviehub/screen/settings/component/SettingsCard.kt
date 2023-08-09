package com.ss.moviehub.screen.settings.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ss.moviehub.ui.common.DefaultText

@Composable
fun SettingsCard(@StringRes title: Int, content: @Composable ColumnScope.() -> Unit) = Card(
    shape = RoundedCornerShape(8.dp),
    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
) {
    ListItem(
        headlineContent = {
            DefaultText(title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.Unspecified,
            leadingIconColor = MaterialTheme.colorScheme.primary
        )
    )

    content()
}
package com.ss.moviehub.screen.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ss.moviehub.BuildConfig
import com.ss.moviehub.R
import com.ss.moviehub.screen.settings.component.SettingsCard
import com.ss.moviehub.screen.settings.component.SettingsItem
import com.ss.moviehub.ui.common.DefaultCenterTopAppBar
import com.ss.moviehub.ui.common.DefaultIcon
import com.ss.moviehub.ui.common.DefaultScaffold
import com.ss.moviehub.ui.theme.MovieHubTheme

@Composable
fun SettingsScreen(onContactClick: () -> Unit) {
    var aboutDialog by remember { mutableStateOf(false) }

    DefaultScaffold(topBar = { SettingsTopAppBar() }) {
        About(
            onContactClick = onContactClick,
            onAppVersionClick = { aboutDialog = true }
        )
    }

    if (aboutDialog) AboutDialog { aboutDialog = false }
}

@Composable
private fun SettingsTopAppBar() = DefaultCenterTopAppBar(title = R.string.settings_screen)

@Composable
private fun About(
    onContactClick: () -> Unit,
    onAppVersionClick: () -> Unit
) = SettingsCard(title = R.string.settings_about) {
    SettingsItem(
        title = R.string.settings_contact_title,
        icon = Icons.Default.ContactSupport,
        onClick = onContactClick
    )
    SettingsItem(
        title = R.string.settings_version_title,
        icon = Icons.Default.Info,
        onClick = onAppVersionClick
    )
}

@Composable
private fun AboutDialog(onDismiss: () -> Unit) = AlertDialog(
    onDismissRequest = onDismiss,
    icon = { DefaultIcon(painter = R.drawable.icon_movies, modifier = Modifier.size(50.dp)) },
    title = {
        val build = BuildConfig.VERSION_CODE
        val version = BuildConfig.VERSION_NAME
        val appVersion = stringResource(id = R.string.settings_version_message, build, version)
        Text(text = appVersion, textAlign = TextAlign.Center)
    },
    confirmButton = {}
)

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "AR")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "EN")
fun SettingsScreenPreview() = MovieHubTheme {
    SettingsScreen(onContactClick = {})
}
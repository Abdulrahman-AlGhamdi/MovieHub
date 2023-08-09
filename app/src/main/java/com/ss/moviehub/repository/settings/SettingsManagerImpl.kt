package com.ss.moviehub.repository.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsManager {

    override fun openTwitter() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TWITTER_ACCOUNT))
        val chooser = Intent.createChooser(intent, "Open app")
        chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(context, chooser, null)
    }

    private companion object SettingsConstants {
        private const val TWITTER_ACCOUNT = "https://twitter.com/xghamdii"
    }
}
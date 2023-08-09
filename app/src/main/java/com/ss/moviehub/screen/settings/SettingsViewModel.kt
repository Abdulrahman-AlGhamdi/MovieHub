package com.ss.moviehub.screen.settings

import androidx.lifecycle.ViewModel
import com.ss.moviehub.repository.settings.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsManager
) : ViewModel() {

    fun openTwitter() = settingsRepository.openTwitter()
}
package com.ss.moviehub

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        defaultSettings()
    }

    private fun defaultSettings() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val darkMode = preferences.getBoolean(getString(R.string.preference_dark_mode_key), false)

        if (darkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
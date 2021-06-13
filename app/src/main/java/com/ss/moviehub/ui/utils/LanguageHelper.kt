package com.ss.universitiesdirectory.utils

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.preference.PreferenceManager
import java.util.*

object LanguageHelper {

    private const val CURRENT_LANGUAGE = "current language"

    fun onBaseAttach(context: Context): Context {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        val language = preference.getString(CURRENT_LANGUAGE, Locale.getDefault().language) ?: "en"
        return updateResources(context, language)
    }

    fun changeCurrentLanguage(context: Context, language: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
            this.putString(CURRENT_LANGUAGE, language)
            this.apply()
        }

        updateResources(context, language)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            context.startActivity(Intent.makeRestartActivityTask(intent?.component))
            Runtime.getRuntime().exit(0)
        }, 200)
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        return context.apply {
            this.resources.configuration.apply {
                this.setLocale(locale)
                this.setLayoutDirection(locale)
                createConfigurationContext(this)
            }
        }
    }
}
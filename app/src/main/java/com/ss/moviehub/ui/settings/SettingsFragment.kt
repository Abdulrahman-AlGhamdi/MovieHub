package com.ss.moviehub.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ss.moviehub.BuildConfig
import com.ss.moviehub.R
import com.ss.universitiesdirectory.utils.LanguageHelper

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        onPreferenceClick()
        showData()
    }

    private fun onPreferenceClick() {
        val darkMode = findPreference(getString(R.string.preference_dark_mode_key)) as? Preference
        val contactMe = findPreference(getString(R.string.preference_contact_me_key)) as? Preference
        val language = findPreference(getString(R.string.preference_language_key)) as? Preference

        darkMode?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue as Boolean) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            true
        }

        language?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue as Boolean) LanguageHelper.changeCurrentLanguage(requireActivity(), "ar")
            else LanguageHelper.changeCurrentLanguage(requireActivity(), "en")
            true
        }

        contactMe?.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("Abdulrahman.SS.AlGhamdi@Gmail.Com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "${requireContext().packageName}: suggestion/issue email")
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Sending Email"))
            true
        }
    }

    private fun showData() {
        val version = findPreference(getString(R.string.preference_version_key)) as? Preference
        version?.summary = getString(R.string.preference_version_summary, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }
}
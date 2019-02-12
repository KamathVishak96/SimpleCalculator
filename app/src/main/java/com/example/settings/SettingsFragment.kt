package com.example.settings

import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.preference.PreferenceFragmentCompat
import com.example.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(bundle: Bundle?, rootkey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootkey)

        val prefSwitchTheme = findPreference("switch_theme")
        prefSwitchTheme.setOnPreferenceChangeListener { _, enabled ->
            if(enabled as Boolean) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                activity?.application?.setTheme(R.style.AppThemeDark)
            }
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            return@setOnPreferenceChangeListener true
        }
    }


}

package com.example.goodfood.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.goodfood.R
import com.example.goodfood.ui.MainActivity
import com.example.goodfood.untils.LocaleHelper
import com.example.goodfood.untils.ThemeHelper


class SettingsFragment() : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPreference: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        sharedPreference = preferenceScreen.sharedPreferences
        sharedPreference.registerOnSharedPreferenceChangeListener(this)

        val signOutBtn = findPreference<Preference>("account_sign_out")
        signOutBtn?.setOnPreferenceClickListener {
            (activity as MainActivity).singOut()
            return@setOnPreferenceClickListener true
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        key?.let {
            val preference = findPreference<ListPreference>(it)
            when(it){
                "them_options" -> {changeThemeMode(preference!!)}
                "ui_language" -> {changeLanguage(preference!!)}
            }
        }
    }

    private fun changeThemeMode(preference: ListPreference){
        val value = preference.value
        ThemeHelper.applyTheme(value)
    }

    private fun changeLanguage(preference: ListPreference){
        Log.e("LANGUAGE", preference.value)
        LocaleHelper.setLocale(requireContext(), preference.value)
        activity?.recreate()
    }

    override fun onDestroy() {
        sharedPreference.unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }
}

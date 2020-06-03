package com.example.goodfood.untils

import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {
    private const val lightMode = "light_mode"
    private const val darkMode = "dark_mode"
    private const val batterySaverMode = "low_battery_mode"
    private const val default = "default"

    fun applyTheme(theme: String) {
        when (theme) {
            lightMode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            darkMode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            batterySaverMode -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            default -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
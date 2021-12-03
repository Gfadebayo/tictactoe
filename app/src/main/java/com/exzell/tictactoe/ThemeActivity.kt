package com.exzell.tictactoe

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.exzell.tictactoe.fragment.SettingFragment

open class ThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(changeTheme())
        super.onCreate(savedInstanceState)
    }

    private fun changeTheme(): Int{
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        return when(pref.getString(SettingFragment.KEY_THEME, "2")) {
            "0" -> AppCompatDelegate.MODE_NIGHT_NO
            "1" -> AppCompatDelegate.MODE_NIGHT_YES

            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }
}
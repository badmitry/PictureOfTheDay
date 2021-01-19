package com.badmitry.pictureoftheday.ui.spsettings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.badmitry.pictureoftheday.mvvm.model.spsettings.ISPSettingsRepo


class SPSettingsRepo(context: Context) : ISPSettingsRepo {
    private val SP_SETTINGS = "settings"
    private val THEME = "theme"
    val settingsSP = context.getSharedPreferences(SP_SETTINGS, MODE_PRIVATE)
    val editor = settingsSP.edit()

    override fun saveTheme(themeId: Int) {
        editor.putInt(THEME, themeId)
        editor.apply()
    }

    override fun getTheme(): Int = settingsSP.getInt(THEME, 0)
}
package com.badmitry.pictureoftheday.mvvm.model.spsettings

import io.reactivex.rxjava3.core.Single

interface ISPSettingsRepo {
    fun saveTheme(themeId: Int)
    fun getTheme(): Int
}
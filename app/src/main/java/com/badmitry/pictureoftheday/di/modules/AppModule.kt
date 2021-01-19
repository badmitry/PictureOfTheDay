package com.badmitry.pictureoftheday.di.modules

import com.badmitry.pictureoftheday.mvvm.model.spsettings.ISPSettingsRepo
import com.badmitry.pictureoftheday.ui.App
import com.badmitry.pictureoftheday.ui.spsettings.SPSettingsRepo
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    fun app() = app

    @Provides
    fun getUiSchelduler() = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    fun getSPSettingsRepo(): ISPSettingsRepo = SPSettingsRepo(app)
}
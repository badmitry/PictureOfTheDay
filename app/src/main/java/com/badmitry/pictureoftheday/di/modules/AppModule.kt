package com.badmitry.pictureoftheday.di.modules

import com.badmitry.pictureoftheday.ui.App
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

@Module
class AppModule(private val app: App) {

    @Provides
    fun app() = app

    @Provides
    fun getUiSchelduler() = AndroidSchedulers.mainThread()

//    @Singleton
//    @Provides
//    fun getNetworkChecker(app: App): INetworkChecker = AndroidNetworkChecker(app)
}
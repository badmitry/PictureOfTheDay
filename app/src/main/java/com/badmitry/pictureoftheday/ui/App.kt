package com.badmitry.pictureoftheday.ui

import android.app.Application
import com.badmitry.pictureoftheday.di.AppComponent
import com.badmitry.pictureoftheday.di.DaggerAppComponent
import com.badmitry.pictureoftheday.di.modules.AppModule
import io.reactivex.rxjava3.plugins.RxJavaPlugins

class App: Application() {
    companion object {
        lateinit var instance: App
        val component get() = instance._appComponent
    }

    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { throwable: Throwable? -> {} }
        instance = this
        _appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
package com.badmitry.pictureoftheday.di

import com.badmitry.pictureoftheday.di.modules.*
import com.badmitry.pictureoftheday.mvvm.vm.MainViewModel
import com.badmitry.pictureoftheday.ui.activities.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        RepoModule::class,
        ViewModulModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainViewModel: MainViewModel)
}
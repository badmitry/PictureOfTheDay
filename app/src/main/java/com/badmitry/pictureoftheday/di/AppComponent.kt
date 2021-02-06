package com.badmitry.pictureoftheday.di

import com.badmitry.pictureoftheday.di.modules.*
import com.badmitry.pictureoftheday.mvvm.vm.MainViewModel
import com.badmitry.pictureoftheday.ui.activities.MainActivity
import com.badmitry.pictureoftheday.ui.activities.NoteActivity
import com.badmitry.pictureoftheday.ui.activities.SplashActivity
import com.badmitry.pictureoftheday.ui.fragments.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        CacheModule::class,
        NavigationModule::class,
        RepoModule::class,
        ViewModulModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainViewModel: MainViewModel)
    fun inject(baseFragment: BaseFragment)
    fun inject(noteActivity: NoteActivity)
    fun inject(splashActivity: SplashActivity)
}
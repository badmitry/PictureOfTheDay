package com.badmitry.pictureoftheday.di.modules

import com.badmitry.pictureoftheday.mvvm.vm.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModulModule {
    @Singleton
    @Provides
    fun getMainViewModel() = MainViewModel()
}
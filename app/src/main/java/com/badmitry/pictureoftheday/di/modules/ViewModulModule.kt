package com.badmitry.pictureoftheday.di.modules

import com.badmitry.pictureoftheday.mvvm.model.repo.INasaRepo
import com.badmitry.pictureoftheday.mvvm.model.spsettings.ISPSettingsRepo
import com.badmitry.pictureoftheday.mvvm.vm.FragmentViewModel
import com.badmitry.pictureoftheday.mvvm.vm.MainViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class ViewModulModule {

    @Singleton
    @Provides
    fun getMainViewModel(
        nasaRepo: INasaRepo,
        uiSchedulers: Scheduler,
        spSettingsRepo: ISPSettingsRepo,
        router: Router
    ) = MainViewModel(nasaRepo, uiSchedulers, spSettingsRepo, router)

    @Provides
    fun getFragmentViewModel(
        nasaRepo: INasaRepo,
        uiSchedulers: Scheduler,
        spSettingsRepo: ISPSettingsRepo
    ) = FragmentViewModel(nasaRepo, uiSchedulers, spSettingsRepo)
}
package com.badmitry.pictureoftheday.mvvm.vm

import androidx.lifecycle.ViewModel
import com.badmitry.pictureoftheday.mvvm.model.repo.INasaRepo
import com.badmitry.pictureoftheday.mvvm.model.spsettings.ISPSettingsRepo
import com.badmitry.pictureoftheday.navigation.Screens
import io.reactivex.rxjava3.core.Scheduler
import ru.terrakok.cicerone.Router

class MainViewModel(
    private val nasaRepo: INasaRepo,
    private val uiSchedulers: Scheduler,
    private val spRepo: ISPSettingsRepo,
    private val router: Router
) :
    ViewModel() {

    var theme = downloadTheme()

    fun changeTheme(themeId: Int) {
        spRepo.saveTheme(themeId)
        theme = downloadTheme()
    }

    fun downloadTheme(): Int = spRepo.getTheme()

    fun navigateTo(numberOfFragment: Int){
        router.replaceScreen(Screens.FragmentScreen(numberOfFragment))
    }
}
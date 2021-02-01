package com.badmitry.pictureoftheday.mvvm.vm

import androidx.lifecycle.ViewModel
import com.badmitry.pictureoftheday.mvvm.model.spsettings.ISPSettingsRepo
import com.badmitry.pictureoftheday.navigation.Screens
import ru.terrakok.cicerone.Router

class MainViewModel(
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

    fun navigateTo(numberOfFragment: Int) {
        router.replaceScreen(Screens.FragmentScreen(numberOfFragment))
    }

    fun navigateToNote() {
        router.navigateTo(Screens.NoteScreen())
    }
}
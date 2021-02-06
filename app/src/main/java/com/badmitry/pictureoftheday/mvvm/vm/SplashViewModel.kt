package com.badmitry.pictureoftheday.mvvm.vm

import com.badmitry.pictureoftheday.navigation.Screens
import ru.terrakok.cicerone.Router

class SplashViewModel(private val router: Router) {
    fun navigateTo() {
        router.navigateTo(Screens.MainScreen())
    }
}
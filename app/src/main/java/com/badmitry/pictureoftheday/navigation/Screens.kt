package com.badmitry.pictureoftheday.navigation

import com.badmitry.pictureoftheday.ui.fragments.BaseFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class FragmentScreen(private val numberOfFragment: Int) : SupportAppScreen() {
        override fun getFragment() = BaseFragment.newInstance(numberOfFragment)
    }
}
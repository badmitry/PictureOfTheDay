package com.badmitry.pictureoftheday.navigation

import android.content.Context
import android.content.Intent
import com.badmitry.pictureoftheday.ui.activities.MainActivity
import com.badmitry.pictureoftheday.ui.activities.NoteActivity
import com.badmitry.pictureoftheday.ui.fragments.BaseFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class FragmentScreen(private val numberOfFragment: Int) : SupportAppScreen() {
        override fun getFragment() = BaseFragment.newInstance(numberOfFragment)
    }

    class NoteScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?) =
            Intent(context, NoteActivity::class.java)
    }

    class MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?) =
            Intent(context, MainActivity::class.java)
    }
}
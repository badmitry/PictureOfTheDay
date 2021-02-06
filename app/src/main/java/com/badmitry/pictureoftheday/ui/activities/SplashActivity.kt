package com.badmitry.pictureoftheday.ui.activities

import android.animation.Animator
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.badmitry.pictureoftheday.R
import com.badmitry.pictureoftheday.databinding.SplashLayoutBinding
import com.badmitry.pictureoftheday.mvvm.vm.SplashViewModel
import com.badmitry.pictureoftheday.ui.App
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    private var binding: SplashLayoutBinding? = null

    @Inject
    lateinit var viewModel: SplashViewModel

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_layout)
        binding?.imageView?.animate()?.rotation(1000f)?.setInterpolator(LinearInterpolator())
            ?.setDuration(3000)?.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                viewModel.navigateTo()
                finish()
            }
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
        })
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val navigator = SupportAppNavigator(this, 0)
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
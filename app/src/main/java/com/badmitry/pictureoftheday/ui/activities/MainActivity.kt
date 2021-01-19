package com.badmitry.pictureoftheday.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.badmitry.pictureoftheday.R
import com.badmitry.pictureoftheday.databinding.MainLayoutBinding
import com.badmitry.pictureoftheday.mvvm.vm.MainViewModel
import com.badmitry.pictureoftheday.ui.App
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private var binding: MainLayoutBinding? = null

    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        setTheme(viewModel.theme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        setBottomAppBar()
        binding?.let {
            it.tabLayout.addTab(it.tabLayout.newTab().setText("Текущая дата").setIcon(R.drawable.ic_baseline_image_search_24))
            it.tabLayout.addTab(it.tabLayout.newTab().setText("Вчерашняя дата").setIcon(R.drawable.ic_baseline_image_search_24))
            it.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    if (it.tabLayout.getSelectedTabPosition() == 0) {
                        viewModel.navigateTo(it.tabLayout.selectedTabPosition)
                    } else {
                        viewModel.navigateTo(it.tabLayout.selectedTabPosition)
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
        viewModel.navigateTo(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_theme -> {
                viewModel.changeTheme(R.style.AppTheme)
                recreate()
            }
            R.id.mars_theme -> {
                viewModel.changeTheme(R.style.MarsTheme)
                recreate()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar() {
        binding?.let {
            setSupportActionBar(it.bottomAppBar)
        }
    }

    override fun onBackPressed() {
        this.finish()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
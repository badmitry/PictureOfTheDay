package com.badmitry.pictureoftheday.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import coil.api.load
import com.badmitry.pictureoftheday.BuildConfig
import com.badmitry.pictureoftheday.R
import com.badmitry.pictureoftheday.databinding.MainLayoutBinding
import com.badmitry.pictureoftheday.mvvm.vm.MainViewModel
import com.badmitry.pictureoftheday.ui.App
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private var binding: MainLayoutBinding? = null

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        setBottomAppBar()
        viewModel.getNasaRequestLiveData().observe(this, { value ->
            binding?.let {
                if (value.mediaType == "image") {
                    binding?.imageView?.load(value.hdurl)
                } else {
                    binding?.imageView?.load(value.thumbnail_url)
                }
            }
        })
        viewModel.getStartWikiLiveData().observe(this, {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding?.inputEditText?.text.toString()}")
            })
        })
        viewModel.initDagger()
        viewModel.getNasaRequest(BuildConfig.NASA_API_KEY)
        binding?.let {
            it.inputLayout.setEndIconOnClickListener {
                viewModel.startWiki()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(this, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_search -> Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar() {
        binding?.let {
            setSupportActionBar(it.bottomAppBar)
        }
//        setHasOptionsMenu(true)
    }

}
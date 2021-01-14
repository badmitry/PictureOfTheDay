package com.badmitry.pictureoftheday.mvvm.vm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.badmitry.pictureoftheday.mvvm.model.entity.NasaRequest
import com.badmitry.pictureoftheday.mvvm.model.repo.INasaRepo
import com.badmitry.pictureoftheday.ui.App
import io.reactivex.rxjava3.core.Scheduler
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainViewModel : ViewModel() {
    @Inject
    lateinit var nasaRepo: INasaRepo

    @Inject
    lateinit var uiSchedulers: Scheduler

    private val nasaRequestLiveData = MutableLiveData<NasaRequest>()
    fun getNasaRequestLiveData() = nasaRequestLiveData
    private val startWikipedia = MutableLiveData<Unit>()
    fun getStartWikiLiveData() = startWikipedia

    fun initDagger() {
        App.component.inject(this)
    }

    @SuppressLint("SimpleDateFormat")
    fun getNasaRequest(apiKey: String) {
        val currentTime = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd")
        nasaRepo.downloadNasaRequest(df.format(currentTime), apiKey).observeOn(uiSchedulers).subscribe({request ->
            nasaRequestLiveData.value = request
        },{
            println("Error: ${it.message}")
        })
    }

    fun startWiki() {
        startWikipedia.value = Unit
    }
}
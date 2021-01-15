package com.badmitry.pictureoftheday.mvvm.vm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.badmitry.pictureoftheday.mvvm.model.entity.NasaRequest
import com.badmitry.pictureoftheday.mvvm.model.repo.INasaRepo
import com.badmitry.pictureoftheday.mvvm.model.spsettings.ISPSettingsRepo
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val nasaRepo: INasaRepo, private val uiSchedulers: Scheduler, private val spRepo: ISPSettingsRepo) :
    ViewModel() {

    var theme = downloadTheme()

    private val compositeDisposable = CompositeDisposable()
    private val nasaRequestLiveData = MutableLiveData<NasaRequest>()
    fun getNasaRequestLiveData() = nasaRequestLiveData
    private val startWikipediaLiveData = MutableLiveData<Unit>()
    fun getStartWikiLiveData() = startWikipediaLiveData

    @SuppressLint("SimpleDateFormat")
    fun getNasaRequest(apiKey: String) {
        val currentTime = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd")
        nasaRepo.downloadNasaRequest(df.format(currentTime), apiKey).observeOn(uiSchedulers)
            .subscribe({ request ->
                nasaRequestLiveData.value = request
            }, {
                println("Error: ${it.message}")
            }).addTo(compositeDisposable)
    }

    fun startWiki() {
        startWikipediaLiveData.value = Unit
    }

    fun stop() {
        compositeDisposable.dispose()
    }

    fun changeTheme(themeId: Int) {
        spRepo.saveTheme(themeId)
        theme = downloadTheme()
    }

    fun downloadTheme(): Int = spRepo.getTheme()
}
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
import ru.terrakok.cicerone.Router
import java.text.SimpleDateFormat
import java.util.*

class FragmentViewModel(
    private val nasaRepo: INasaRepo,
    private val uiSchedulers: Scheduler,
    private val spRepo: ISPSettingsRepo
) :
    ViewModel() {

    var numberFragment = 0

    var theme = downloadTheme()

    private val compositeDisposable = CompositeDisposable()
    private val nasaRequestLiveData = MutableLiveData<NasaRequest>()
    fun getNasaRequestLiveData() = nasaRequestLiveData
    private val startWikipediaLiveData = MutableLiveData<Unit>()
    fun getStartWikiLiveData() = startWikipediaLiveData

    @SuppressLint("SimpleDateFormat")
    fun getNasaRequest(apiKey: String) {
        val currentTime = Calendar.getInstance()
        if (numberFragment == 1) {
            currentTime.add(Calendar.DAY_OF_MONTH, -1)
        }
        val df = SimpleDateFormat("yyyy-MM-dd")
        nasaRepo.downloadNasaRequest(df.format(currentTime.time), apiKey).observeOn(uiSchedulers)
            .subscribe({ request ->
                nasaRequestLiveData.value = request
            }, {
                println("Error: ${it.message}")
            }).addTo(compositeDisposable)
    }

    fun startWiki() {
        startWikipediaLiveData.value = Unit
    }

    public override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun changeTheme(themeId: Int) {
        spRepo.saveTheme(themeId)
        theme = downloadTheme()
    }

    fun downloadTheme(): Int = spRepo.getTheme()
}
package com.badmitry.pictureoftheday.mvvm.model.repo

import com.badmitry.pictureoftheday.mvvm.model.entity.NasaRequest
import io.reactivex.rxjava3.core.Single

interface INasaRepo {
    fun downloadNasaRequest(date: String, apiKey: String): Single<NasaRequest>
}
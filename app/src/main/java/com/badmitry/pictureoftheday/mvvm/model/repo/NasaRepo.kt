package com.badmitry.pictureoftheday.mvvm.model.repo

import com.badmitry.pictureoftheday.mvvm.model.api.IDataSource
import com.badmitry.pictureoftheday.mvvm.model.entity.NasaRequest
import com.badmitry.pictureoftheday.mvvm.model.error.NerworkIsNotAvailable
import com.badmitry.pictureoftheday.mvvm.model.network.INetworkChecker
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class NasaRepo(
    private val api: IDataSource,
    private val networkChecker: INetworkChecker
) : INasaRepo {

    override fun downloadNasaRequest(date: String, apiKey: String): Single<NasaRequest> =
        networkChecker.isConnect().flatMap {
            if (it) {
                return@flatMap api.getNasaRequest(date, apiKey).subscribeOn(Schedulers.io())
            } else {
                return@flatMap Single.create<NasaRequest> {emitter ->
                    emitter.onError(NerworkIsNotAvailable("Net connection is not available"))
                }.subscribeOn(Schedulers.io())
            }
        }.subscribeOn(Schedulers.io())


}
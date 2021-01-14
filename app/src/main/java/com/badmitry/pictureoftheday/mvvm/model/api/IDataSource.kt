package com.badmitry.pictureoftheday.mvvm.model.api

import com.badmitry.pictureoftheday.mvvm.model.entity.NasaRequest
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSource {
    @GET("/planetary/apod?")
    fun getNasaRequest(
        @Query("date") date: String,
        @Query("api_key") apiKey: String,
        @Query("thumbs") thumbs: String = "true"
    ): Single<NasaRequest>
}
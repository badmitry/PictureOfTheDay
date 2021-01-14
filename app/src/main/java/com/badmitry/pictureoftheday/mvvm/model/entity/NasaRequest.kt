package com.badmitry.pictureoftheday.mvvm.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NasaRequest(
    @Expose val date: String,
    @Expose val explanation: String,
    @Expose val hdurl: String,
    @Expose val mediaType: String,
    @Expose val thumbnail_url: String? = null,
    @Expose val title: String,
    @Expose val url: String
) : Parcelable
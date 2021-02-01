package com.badmitry.pictureoftheday.mvvm.model.room

import com.badmitry.pictureoftheday.mvvm.model.entity.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface INoteCache {
    fun loadInCache(note: Note): Completable
    fun deleteFromCache(noteId: Int): Completable
    fun takeAllFromCache(): Single<List<Note>>
}
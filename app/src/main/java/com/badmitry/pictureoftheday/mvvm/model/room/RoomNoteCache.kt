package com.badmitry.pictureoftheday.mvvm.model.room

import com.badmitry.pictureoftheday.mvvm.model.entity.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomNoteCache(private val db: Database) :
    INoteCache {
    override fun loadInCache(note: Note) = Completable.fromAction {
        val roomNote = RoomNote(
            note.noteId,
            note.note
        )
        db.noteDao.insert(roomNote)
    }.subscribeOn(Schedulers.io())

    override fun deleteFromCache(noteId: Int): Completable = Completable.fromAction {
        db.noteDao.deleteOnId(noteId)
    }.subscribeOn(Schedulers.io())

    override fun takeAllFromCache() = Single.fromCallable {
        db.noteDao.getAll().map { roomFuture ->
            Note(
                roomFuture.noteId,
                roomFuture.note
            )
        }
    }.subscribeOn(Schedulers.io())
}
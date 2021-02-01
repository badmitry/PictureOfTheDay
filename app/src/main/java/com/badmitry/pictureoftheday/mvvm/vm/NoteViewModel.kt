package com.badmitry.pictureoftheday.mvvm.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.badmitry.pictureoftheday.mvvm.model.entity.Note
import com.badmitry.pictureoftheday.mvvm.model.room.INoteCache
import com.badmitry.pictureoftheday.mvvm.model.spsettings.ISPSettingsRepo
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class NoteViewModel(
    private val spRepo: ISPSettingsRepo,
    private val cache: INoteCache,
    private val uiSchedulers: Scheduler
) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val notes = mutableListOf<Note>()
    private val noteListLiveData = MutableLiveData<MutableList<Note>>()
    fun getNoteListLiveData() = noteListLiveData

    var theme = downloadTheme()

    fun getNotesFromCache() {
        cache.takeAllFromCache().observeOn(uiSchedulers).subscribe({repos ->
            notes.clear()
            notes.addAll(repos)
            noteListLiveData.value = notes
        },{
            println("Error: ${it.message}")
        }).addTo(compositeDisposable)
    }

    fun setChangeNotesToCache(note: Note) {
        cache.loadInCache(note).observeOn(uiSchedulers).subscribe()
    }

    fun removeNoteFromCache(noteId: Int) {
        cache.deleteFromCache(noteId).observeOn(uiSchedulers).subscribe()
    }

    fun downloadTheme(): Int = spRepo.getTheme()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
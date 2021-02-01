package com.badmitry.pictureoftheday.mvvm.model.entity

data class Note(val noteId: Int, val note: String = "") {
    operator fun compareTo(f: Note): Int {
        return if (noteId > f.noteId) {
            1
        } else if (noteId < f.noteId) {
            -1
        } else {
            0
        }
    }
}
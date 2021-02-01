package com.badmitry.pictureoftheday.mvvm.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomNote (
    @PrimaryKey var noteId: Int,
    var note: String
)
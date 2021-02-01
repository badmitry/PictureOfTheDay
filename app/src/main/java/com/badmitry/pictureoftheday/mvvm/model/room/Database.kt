package com.badmitry.pictureoftheday.mvvm.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.badmitry.pictureoftheday.mvvm.model.room.dao.NoteDao

@Database(entities = [RoomNote::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DB_NAME = "database.db"
        private var instance: com.badmitry.pictureoftheday.mvvm.model.room.Database? = null
        fun create(context: Context) {
            instance ?: let {
                instance = Room.databaseBuilder(context, com.badmitry.pictureoftheday.mvvm.model.room.Database::class.java, DB_NAME)
                    .build()
            }
        }
    }
}
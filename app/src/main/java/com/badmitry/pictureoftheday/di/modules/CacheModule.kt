package com.badmitry.pictureoftheday.di.modules

import androidx.room.Room
import com.badmitry.pictureoftheday.mvvm.model.room.Database
import com.badmitry.pictureoftheday.mvvm.model.room.INoteCache
import com.badmitry.pictureoftheday.mvvm.model.room.RoomNoteCache
import com.badmitry.pictureoftheday.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @Provides
    fun getDatabase(app: App) =
        Room.databaseBuilder(
            app,
            Database::class.java,
            Database.DB_NAME
        )
            .build()

    @Singleton
    @Provides
    fun getFutureCache(database: Database): INoteCache = RoomNoteCache(database)
}
package com.badmitry.pictureoftheday.mvvm.model.room.dao

import androidx.room.*
import com.badmitry.pictureoftheday.mvvm.model.room.RoomNote

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: RoomNote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repo: RoomNote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: List<RoomNote>)

    @Delete
    fun delete(repo: RoomNote)

    @Delete
    fun delete(vararg repo: RoomNote)

    @Delete
    fun delete(repo: List<RoomNote>)

    @Update
    fun update(repo: RoomNote)

    @Update
    fun update(vararg repo: RoomNote)

    @Update
    fun update(repo: List<RoomNote>)

    @Query("SELECT * FROM RoomNote")
    fun getAll(): List<RoomNote>

    @Query("DELETE FROM RoomNote WHERE noteId = :noteId")
    fun deleteOnId(noteId: Int)
}
package com.example.shift_lab.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shift_lab.data.model.NoteModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewNote(note: NoteModel)

    @Query("SELECT * FROM notemodel")
    suspend fun getAllNotes(): List<NoteModel>

    @Query("DELETE FROM notemodel WHERE id = :id")
    suspend fun deleteNote(id: Int)

}
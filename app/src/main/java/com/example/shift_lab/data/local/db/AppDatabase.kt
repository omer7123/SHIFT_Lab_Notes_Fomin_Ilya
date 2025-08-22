package com.example.shift_lab.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shift_lab.data.model.NoteModel

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
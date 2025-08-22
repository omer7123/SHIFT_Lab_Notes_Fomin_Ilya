package com.example.shift_lab.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shift_lab.data.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): NoteDao
}
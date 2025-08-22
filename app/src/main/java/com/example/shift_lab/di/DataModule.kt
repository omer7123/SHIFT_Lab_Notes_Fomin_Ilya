package com.example.shift_lab.di

import android.content.Context
import androidx.room.Room
import com.example.shift_lab.data.local.db.AppDatabase
import com.example.shift_lab.data.local.db.NoteDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "note_db"
        ).build()
    }

    @Provides
    fun provideLoanDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao()
    }
}
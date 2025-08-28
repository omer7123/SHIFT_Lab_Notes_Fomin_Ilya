package com.example.shift_lab.data.local.room

import com.example.shift_lab.core.Resource
import com.example.shift_lab.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    suspend fun createNewNote(noteModel: NoteModel): Flow<Resource<Unit>>
    suspend fun getAllNotes(): Flow<Resource<List<NoteModel>>>
    suspend fun deleteNote(id: Int): Flow<Resource<Unit>>
    suspend fun getNoteById(id: Int): Flow<Resource<NoteModel>>
    suspend fun updateNote(noteModel: NoteModel): Flow<Resource<Unit>>

}
package com.example.shift_lab.domain.repository

import com.example.shift_lab.core.Resource
import com.example.shift_lab.domain.entity.NoteCreateEntity
import com.example.shift_lab.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun createNewNote(createNote: NoteCreateEntity): Flow<Resource<Unit>>
    suspend fun getAllNotes(): Flow<Resource<List<NoteEntity>>>
    suspend fun deleteNote(id: Int): Flow<Resource<Unit>>

}
package com.example.shift_lab.data.repository

import com.example.shift_lab.core.Resource
import com.example.shift_lab.core.checkResource
import com.example.shift_lab.data.converters.toEntity
import com.example.shift_lab.data.converters.toModel
import com.example.shift_lab.data.local.room.NoteDataSource
import com.example.shift_lab.domain.entity.NoteCreateEntity
import com.example.shift_lab.domain.entity.NoteEntity
import com.example.shift_lab.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val dataSource: NoteDataSource) :
    NoteRepository {
    override suspend fun createNewNote(createNote: NoteCreateEntity): Flow<Resource<Unit>> {
        return dataSource.createNewNote(createNote.toModel()).checkResource{ it }
    }

    override suspend fun getAllNotes(): Flow<Resource<List<NoteEntity>>> {
        return dataSource.getAllNotes().checkResource { list->
            list.map {
                it.toEntity()
            }
        }
    }

}
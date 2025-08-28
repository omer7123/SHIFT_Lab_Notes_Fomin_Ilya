package com.example.shift_lab.data.local.room

import android.content.Context
import com.example.shift_lab.core.BaseLocalDataSource
import com.example.shift_lab.core.Resource
import com.example.shift_lab.data.local.db.NoteDao
import com.example.shift_lab.data.model.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RoomDataSourceImpl @Inject constructor(
    private val db: NoteDao, context: Context
) : NoteDataSource, BaseLocalDataSource(context) {

    override suspend fun createNewNote(noteModel: NoteModel): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)

        emit(
            saveResult {
                db.createNewNote(noteModel)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllNotes(): Flow<Resource<List<NoteModel>>> = flow{
        emit(Resource.Loading)

        emit(
            getResult {
                db.getAllNotes()
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteNote(id: Int): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading)

        emit(
            getResult {
                db.deleteNote(id)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun getNoteById(id: Int): Flow<Resource<NoteModel>> = flow{
        emit(Resource.Loading)

        emit(
            getResult {
                db.getNoteById(id)
            }
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun updateNote(noteModel: NoteModel): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading)
        emit(
            getResult {
                db.createNewNote(noteModel)
            }
        )
    }.flowOn(Dispatchers.IO)

}
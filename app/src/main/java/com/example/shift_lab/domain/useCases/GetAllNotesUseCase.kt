package com.example.shift_lab.domain.useCases

import com.example.shift_lab.core.Resource
import com.example.shift_lab.domain.entity.NoteEntity
import com.example.shift_lab.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(): Flow<Resource<List<NoteEntity>>> = repository.getAllNotes()
}
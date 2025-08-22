package com.example.shift_lab.domain.useCases

import com.example.shift_lab.core.Resource
import com.example.shift_lab.domain.entity.NoteCreateEntity
import com.example.shift_lab.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(newNote: NoteCreateEntity): Flow<Resource<Unit>> =
        repository.createNewNote(newNote)
}
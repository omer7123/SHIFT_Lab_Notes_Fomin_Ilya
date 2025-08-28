package com.example.shift_lab.domain.useCases

import com.example.shift_lab.domain.entity.NoteEntity
import com.example.shift_lab.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(note: NoteEntity) = repository.updateNote(note)
}
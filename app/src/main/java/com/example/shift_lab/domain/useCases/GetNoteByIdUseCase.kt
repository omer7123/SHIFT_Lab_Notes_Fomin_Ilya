package com.example.shift_lab.domain.useCases

import com.example.shift_lab.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val repository: NoteRepository){
    suspend operator fun invoke(id: Int) = repository.getNoteById(id)
}
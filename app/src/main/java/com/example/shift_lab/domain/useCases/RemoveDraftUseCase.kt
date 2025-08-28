package com.example.shift_lab.domain.useCases

import com.example.shift_lab.domain.repository.NoteRepository
import javax.inject.Inject

class RemoveDraftUseCase @Inject constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Int) = repository.removeDraft(id)

}
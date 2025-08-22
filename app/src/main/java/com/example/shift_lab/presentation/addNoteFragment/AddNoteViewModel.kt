package com.example.shift_lab.presentation.addNoteFragment

import androidx.lifecycle.ViewModel
import com.example.shift_lab.domain.useCases.CreateNoteUseCase
import javax.inject.Inject

class AddNoteViewModel @Inject constructor(
    private val useCase: CreateNoteUseCase
): ViewModel() {

}
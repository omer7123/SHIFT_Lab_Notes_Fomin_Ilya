package com.example.shift_lab.presentation.homeFragment

import com.example.shift_lab.domain.entity.NoteEntity

sealed interface HomeScreenState{
    data class Content(
        val notes: List<NoteEntity>
    ): HomeScreenState

    data object Loading: HomeScreenState
    data object Error: HomeScreenState
}
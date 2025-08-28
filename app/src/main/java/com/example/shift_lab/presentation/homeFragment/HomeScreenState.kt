package com.example.shift_lab.presentation.homeFragment

import com.example.shift_lab.domain.entity.NoteEntityUI

sealed interface HomeScreenState{
    data class Content(
        val notes: List<NoteEntityUI>
    ): HomeScreenState

    data object Loading: HomeScreenState
    data object Error: HomeScreenState
}
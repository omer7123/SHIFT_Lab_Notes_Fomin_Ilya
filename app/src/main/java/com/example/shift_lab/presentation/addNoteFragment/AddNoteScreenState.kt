package com.example.shift_lab.presentation.addNoteFragment

data class AddNoteScreenState(
    val title: String = "",
    val content: String = "",
    val visibleBtnSave: Boolean = false,
    val draft: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false,
    val successSave: Boolean = false,
)
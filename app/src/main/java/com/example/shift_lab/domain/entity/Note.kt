package com.example.shift_lab.domain.entity

data class NoteEntity(
    val id: String,
    val title: String,
    val content: String,
    val dateCreate: String
)

data class NoteCreateEntity(
    val title: String,
    val content: String,
    val dateCreate: String
)
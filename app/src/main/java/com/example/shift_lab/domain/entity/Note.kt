package com.example.shift_lab.domain.entity

import java.time.ZonedDateTime

data class NoteEntity(
    val id: Int,
    val title: String,
    val content: String,
    val dateCreate: ZonedDateTime,
    val draft: Boolean
)

data class NoteEntityUI(
    val id: Int,
    val title: String,
    val content: String,
    val dateCreate: String,
)

data class NoteCreateEntity(
    val title: String,
    val content: String,
    val dateCreate: ZonedDateTime,
    val isDraft: Boolean = false,
)
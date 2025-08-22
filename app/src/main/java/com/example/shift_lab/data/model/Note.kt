package com.example.shift_lab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteModel(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val dateCreate: String
)
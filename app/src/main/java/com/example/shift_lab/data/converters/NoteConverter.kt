package com.example.shift_lab.data.converters

import com.example.shift_lab.data.model.NoteModel
import com.example.shift_lab.domain.entity.NoteCreateEntity
import com.example.shift_lab.domain.entity.NoteEntity
import java.util.UUID

fun NoteCreateEntity.toModel() =
    NoteModel(UUID.randomUUID().toString(), this.title, this.content, this.dateCreate)

fun NoteModel.toEntity() =
    NoteEntity(id, title, content, dateCreate)
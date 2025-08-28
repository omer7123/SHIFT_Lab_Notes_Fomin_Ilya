package com.example.shift_lab.data.converters

import com.example.shift_lab.data.model.NoteModel
import com.example.shift_lab.domain.entity.NoteCreateEntity
import com.example.shift_lab.domain.entity.NoteEntity

fun NoteEntity.toModel() =
    NoteModel(id = this.id, title = this.title, content = this.content, dateCreate = this.dateCreate)

fun NoteCreateEntity.toModel() =
    NoteModel(title = this.title, content = this.content, dateCreate = this.dateCreate)

fun NoteModel.toEntity() =
    NoteEntity(id, title, content, dateCreate)
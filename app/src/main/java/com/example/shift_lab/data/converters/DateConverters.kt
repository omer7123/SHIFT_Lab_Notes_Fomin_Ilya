package com.example.shift_lab.data.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class Converters {

    @TypeConverter
    fun fromZonedDateTime(date: ZonedDateTime?): Long? =
        date?.toInstant()?.toEpochMilli()

    @TypeConverter
    fun toZonedDateTime(timestamp: Long?): ZonedDateTime? =
        timestamp?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()) }
}
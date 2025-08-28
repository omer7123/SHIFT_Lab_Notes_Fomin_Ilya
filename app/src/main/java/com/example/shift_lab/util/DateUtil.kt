package com.example.shift_lab.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatDate(date: ZonedDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy\nHH:mm")
    return date.format(formatter)
}
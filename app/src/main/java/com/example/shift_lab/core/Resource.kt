package com.example.shift_lab.core

sealed class Resource<out T> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error<out T : Any>(val msg: String?, val data: T?) : Resource<T>()
    data object Loading : Resource<Nothing>()
}
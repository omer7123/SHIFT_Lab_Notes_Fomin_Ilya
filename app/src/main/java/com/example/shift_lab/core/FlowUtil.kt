package com.example.shift_lab.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T, E> Flow<Resource<T>>.checkResource(
    transform: (T) -> E
): Flow<Resource<E>>{
    return this.map {
        when(it){
            is Resource.Error -> Resource.Error(it.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(transform(it.data)!!)
        }
    }
}
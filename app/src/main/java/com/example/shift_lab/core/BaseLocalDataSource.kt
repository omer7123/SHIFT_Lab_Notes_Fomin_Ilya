package com.example.shift_lab.core

import android.content.Context
import com.example.shift_lab.R

abstract class BaseLocalDataSource(
    private val context: Context
) {

    protected suspend fun <T : Any> getResult(
        call: suspend () -> T?
    ): Resource<T> {
        return try {
            val res = call()
            if (res != null) {
                Resource.Success(res)
            } else {
                Resource.Error(context.getString(R.string.not_found), null)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: e.toString(), null)
        }
    }

    protected suspend fun saveResult(
        call: suspend () -> Unit
    ): Resource<Unit> {
        return try {
            call()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: e.toString(), null)
        }
    }
}
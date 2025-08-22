package com.example.shift_lab.di

import com.example.shift_lab.data.local.room.NoteDataSource
import com.example.shift_lab.data.local.room.RoomDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindAuthenticationDataSource(impl: RoomDataSourceImpl): NoteDataSource

}
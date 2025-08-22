package com.example.shift_lab.di

import com.example.shift_lab.data.repository.NoteRepositoryImpl
import com.example.shift_lab.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindLoanRepository(impl: NoteRepositoryImpl): NoteRepository

}
package com.example.shift_lab.di

import androidx.lifecycle.ViewModel
import com.example.shift_lab.presentation.addNoteFragment.AddNoteViewModel
import com.example.shift_lab.presentation.homeFragment.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface NoteModule{

    @Binds
    @[IntoMap ClassKey(HomeViewModel::class)]
    fun provideHomeViewModel(newLoanViewModel: HomeViewModel): ViewModel

    @Binds
    @[IntoMap ClassKey(AddNoteViewModel::class)]
    fun provideAddNoteViewModel(newLoanViewModel: AddNoteViewModel): ViewModel
}

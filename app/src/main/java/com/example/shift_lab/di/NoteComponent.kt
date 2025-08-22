package com.example.shift_lab.di

import com.example.shift_lab.ui.addNoteFragment.AddNoteFragment
import com.example.shift_lab.ui.homeFragment.HomeFragment
import dagger.Subcomponent

@Subcomponent(modules = [NoteModule::class])
interface NoteComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: AddNoteFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): NoteComponent
    }
}
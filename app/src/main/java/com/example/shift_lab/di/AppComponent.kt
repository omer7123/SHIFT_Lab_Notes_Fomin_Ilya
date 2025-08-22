package com.example.shift_lab.di

import android.content.Context
import com.example.shift_lab.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, DataModule::class, DataSourceModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun noteComponent(): NoteComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
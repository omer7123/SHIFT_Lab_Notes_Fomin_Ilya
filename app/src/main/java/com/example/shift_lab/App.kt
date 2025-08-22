package com.example.shift_lab

import android.app.Application
import com.example.shift_lab.di.AppComponent
import com.example.shift_lab.di.DaggerAppComponent


class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}
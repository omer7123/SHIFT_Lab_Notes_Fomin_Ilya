package com.example.shift_lab.util

import android.content.Context
import com.example.shift_lab.App
import com.example.shift_lab.di.AppComponent

fun Context.getAppComponent(): AppComponent = (this.applicationContext as App).appComponent
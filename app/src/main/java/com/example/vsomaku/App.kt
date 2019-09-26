package com.example.vsomaku

import android.app.Application
import android.util.Log
import com.example.vsomaku.components.AppComponent
import com.example.vsomaku.components.DaggerAppComponent
import com.example.vsomaku.modules.AppContextModule

class App : Application() {
    lateinit var component : AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .appContextModule(AppContextModule(this))
            .build()

        Log.d(DEBUG_TAG, "${this.javaClass.name} started")
    }
}
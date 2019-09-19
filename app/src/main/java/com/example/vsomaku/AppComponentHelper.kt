package com.example.vsomaku

import com.example.vsomaku.components.AppComponent
import com.example.vsomaku.components.DaggerAppComponent

object AppComponentHelper {
    val appComponent : AppComponent = DaggerAppComponent.create()
}
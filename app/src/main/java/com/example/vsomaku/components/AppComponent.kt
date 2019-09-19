package com.example.vsomaku.components

import com.example.vsomaku.activities.MainActivity
import com.example.vsomaku.activities.PostActivity
import com.example.vsomaku.activities.UserActivity
import com.example.vsomaku.modules.PresentersModule
import com.example.vsomaku.modules.ReposModule
import dagger.Component

@Component(modules = [PresentersModule::class, ReposModule::class])
interface AppComponent {
    fun injectPostsPresenter(activity : MainActivity)
    fun injectPostInfoPresenter(activity : PostActivity)
    fun injectUserInfoPresenter(activity : UserActivity)
}
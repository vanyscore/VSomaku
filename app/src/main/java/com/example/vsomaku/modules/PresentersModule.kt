package com.example.vsomaku.modules

import android.content.Context
import com.example.vsomaku.presenters.PostInfoPresenter
import com.example.vsomaku.presenters.PostsPresenter
import com.example.vsomaku.presenters.UserInfoPresenter
import com.example.vsomaku.repos.PostInfoRepo
import com.example.vsomaku.repos.PostRepo
import com.example.vsomaku.repos.UserInfoRepo
import dagger.Module
import dagger.Provides

@Module
class PresentersModule {

    @Provides
    fun providePostsPresenter(repo : PostRepo, context : Context) : PostsPresenter {
        return PostsPresenter(repo, context)
    }

    @Provides
    fun providePostInfoPresenter(repo : PostInfoRepo) : PostInfoPresenter {
        return PostInfoPresenter(repo)
    }

    @Provides
    fun provideUserInfoPresenter(repo : UserInfoRepo) : UserInfoPresenter {
        return UserInfoPresenter(repo)
    }
}
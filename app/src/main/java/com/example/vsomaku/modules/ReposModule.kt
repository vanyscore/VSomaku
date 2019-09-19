package com.example.vsomaku.modules

import com.example.vsomaku.ApiHelper
import com.example.vsomaku.repos.PostInfoRepo
import com.example.vsomaku.repos.PostRepo
import com.example.vsomaku.repos.UserInfoRepo
import dagger.Module
import dagger.Provides

@Module
class ReposModule {

    @Provides
    fun providePostRepo() : PostRepo {
        return PostRepo(ApiHelper.apiInstance)
    }

    @Provides
    fun providePostInfoRepo() : PostInfoRepo {
        return PostInfoRepo(ApiHelper.apiInstance)
    }

    @Provides
    fun provideUserInfoRepo() : UserInfoRepo {
        return UserInfoRepo(ApiHelper.apiInstance)
    }
}
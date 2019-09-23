package com.example.vsomaku.modules

import com.example.vsomaku.daos.*
import com.example.vsomaku.repos.PostInfoRepo
import com.example.vsomaku.repos.PostRepo
import com.example.vsomaku.repos.UserInfoRepo
import dagger.Module
import dagger.Provides

@Module
class ReposModule {

    @Provides
    fun providePostRepo(dao : PostDao) : PostRepo {
        return PostRepo(dao)
    }

    @Provides
    fun providePostInfoRepo(userDao: UserDao, commentDao: CommentDao) : PostInfoRepo {
        return PostInfoRepo(userDao, commentDao)
    }

    @Provides
    fun provideUserInfoRepo(albumDao: AlbumDao, photoDao: PhotoDao) : UserInfoRepo {
        return UserInfoRepo(albumDao, photoDao)
    }
}
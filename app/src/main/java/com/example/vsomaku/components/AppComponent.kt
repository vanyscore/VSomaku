package com.example.vsomaku.components

import android.content.Context
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.daos.*
import com.example.vsomaku.databases.SomakuDatabase
import com.example.vsomaku.fragments.*
import com.example.vsomaku.modules.*
import com.example.vsomaku.repos.BaseRepo
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppContextModule::class, PresentersModule::class
    , ReposModule::class, DatabaseModule::class, NetworkUtilsModule::class, DaosModule::class])
interface AppComponent {
    fun injectPostsPresenter(fragment : PostsFragment)
    fun injectPostInfoPresenter(fragment : PostInfoFragment)
    fun injectUserInfoPresenter(fragment : UserInfoFragment)



    fun getContext() : Context

    @Singleton
    fun getApi() : SomakuApi

    @Singleton
    fun getDatabase() : SomakuDatabase

    fun getPostDao() : PostDao
    fun getUserDao() : UserDao
    fun getCommentDao() : CommentDao
    fun getAlbumDao() : AlbumDao
    fun getPhotoDao() : PhotoDao
}
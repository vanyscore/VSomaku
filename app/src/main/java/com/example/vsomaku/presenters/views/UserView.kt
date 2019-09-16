package com.example.vsomaku.presenters.views

import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User

interface UserView {
    fun bindUserInfo(user : User)
    fun bindAlbumsInfo(albums : List<Album>, photos : List<Photo>)
    fun showLayout()
}
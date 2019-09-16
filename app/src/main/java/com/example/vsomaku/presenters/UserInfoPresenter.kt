package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.UserView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoPresenter(private val user : User) : BasePresenter<UserView>() {
    private var photos : ArrayList<Photo> = arrayListOf()

    fun getUserInfo() {
        ApiHelper.apiInstance.getAlbums(user.id).enqueue(object : Callback<List<Album>> {
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                Log.d(DEBUG_TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.code() == 200) {
                    val albums = response.body()
                    if (albums != null) {
                        execPhotosCount(albums)
                    }
                }
            }
        })
    }

    private fun execPhotosCount(albums : List<Album>) {
        var albumsCount = 0
        for (album : Album in albums)
            ApiHelper.apiInstance.getPhotos(album.id).enqueue(object : Callback<List<Photo>> {
                override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                    Log.d(DEBUG_TAG, t.localizedMessage)
                }

                override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                    if (response.code() == 200) {
                        val photos = response.body()
                        if (photos != null) {
                            this@UserInfoPresenter.photos.addAll(photos)
                            albumsCount++

                            if (albumsCount == albums.size) {
                                view?.let {
                                    it.bindUserInfo(user)
                                    it.bindAlbumsInfo(albums, this@UserInfoPresenter.photos)
                                    it.showLayout()
                                }
                            }
                        }
                    }
                }
            })
    }
}
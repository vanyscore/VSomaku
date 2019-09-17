package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.UserView
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class UserInfoPresenter(private val user : User) : BasePresenter<UserView>() {
    private var photos : ArrayList<Photo> = arrayListOf()

    fun showUserInfo() {
        view?.showUserInfo(user)
    }

    fun getAlbums() {
        disposable.add(ApiHelper.apiInstance.getAlbums(user.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.code() == 200) {
                    val albums = response.body()
                    if (albums != null) {
                        execPhotosCount(albums)
                    }
                }
            }, {
                Log.d(DEBUG_TAG, it.localizedMessage)
            }))
    }

    private fun execPhotosCount(albums : List<Album>) {
        val flow : ArrayList<Flowable<Response<List<Photo>>>> = arrayListOf()
        for (album : Album in albums) flow.add(ApiHelper.apiInstance.getPhotos(album.id))
        val flows : Flowable<Response<List<Photo>>> = Flowable.merge(flow)
        var checkedAlbums = 0
        disposable.add(flows
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.code() == 200) {
                    Log.d(DEBUG_TAG, response.body()?.size.toString())

                    val photos = response.body()
                    if (photos != null)
                        this@UserInfoPresenter.photos.addAll(photos)

                    if (++checkedAlbums == albums.size) {
                        view?.let {
                            it.bindAlbumsInfo(albums, this@UserInfoPresenter.photos)
                            it.showLayout()
                        }
                    }
                }
            }, {
                Log.d(DEBUG_TAG, it.localizedMessage)
            }))
    }
}
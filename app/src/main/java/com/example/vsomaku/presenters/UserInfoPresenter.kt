package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.UserView
import com.example.vsomaku.repos.UserInfoRepo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class UserInfoPresenter(private val repo : UserInfoRepo) : BasePresenter<UserView>() {
    fun showUserInfo(user : User) {
        view?.showUserInfo(user)
    }

    fun getAlbumsData(userId : Int) {
        repo.loadAlbumsData(Consumer {pair ->
            view?.let {
                it.bindAlbumsInfo(pair.first, pair.second)
                it.showLayout()
            }

        }, Consumer {
            Log.d(DEBUG_TAG, it.localizedMessage)
        }, userId)
    }

    override fun onDestroy() {
        repo.destroy()
    }

}
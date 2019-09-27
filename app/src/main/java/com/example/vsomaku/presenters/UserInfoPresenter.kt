package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.UserView
import com.example.vsomaku.repos.UserInfoRepo
import io.reactivex.functions.Consumer
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.HttpException

@InjectViewState
class UserInfoPresenter(private val repo : UserInfoRepo) : MvpPresenter<UserView>() {
    fun showUserInfo(user : User) {
        viewState.showUserInfo(user)
    }

    fun getAlbumsData(userId : Int) {
        repo.loadAlbumsData(Consumer {pair ->
            viewState.bindAlbumsInfo(pair.first, pair.second)
            viewState.showLayout()
        }, Consumer {
            Log.d(DEBUG_TAG, it.localizedMessage)
        }, userId)
    }

    override fun onDestroy() {
        super.onDestroy()

        repo.destroy()
    }
}
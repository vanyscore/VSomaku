package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.PostInfoView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostInfoPresenter(private val post : Post) : BasePresenter<PostInfoView>() {
    private var reqCount = 0

    fun showPostInfo() {
        view?.bindPostInfo(post)
    }

    fun getComments() {
        disposable.add(ApiHelper.apiInstance.getComments(post.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.code() == 200) {
                    val comments = response.body()
                    if (comments != null) {
                        view?.bindComments(comments!!.map { comment ->
                            Comment(comment.postId,
                                comment.id,
                                comment.name[0].toUpperCase() + comment.name.substring(1),
                                comment.email,
                                comment.text[0].toUpperCase() + comment.text.substring(1))
                        })

                        if (++reqCount == 2) view?.showLayout()
                    }
                }
            }, {
                Log.d(DEBUG_TAG, it.localizedMessage)
            }))
    }

    fun getUserInfo() {
        disposable.add(ApiHelper.apiInstance.getUser(post.userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                if (response.code() == 200) {
                    val users = response.body()
                    if (users != null) {
                        val user = users[0]

                        view?.let {
                            it.bindUserInfo(user)

                            if (++reqCount == 2)
                                it.showLayout()
                        }
                    }
                }
            }, {
                Log.d(DEBUG_TAG, it.localizedMessage)
            }))
    }
}
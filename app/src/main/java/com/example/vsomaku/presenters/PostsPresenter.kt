package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.views.PostsView
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.*
import retrofit2.Response

class PostsPresenter: BasePresenter<PostsView>() {

    fun getPosts() {
        disposable.add(ApiHelper.apiInstance.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ response ->
                if (response.code() == 200) {
                    val posts = response.body()
                    if (posts != null) {
                        view?.let { v ->
                            v.bindPosts(posts.map { post ->
                                Post(
                                    post.userId,
                                    post.id,
                                    post.title?.let {
                                        it[0].toUpperCase() + it.substring(1)
                                    },
                                    post.description?.let {
                                        it[0].toUpperCase() + it.substring(1).replace("\n", "")
                                    }
                                )
                            })
                            v.showLayout()
                        }
                    }
                }
            }, {
                Log.d(DEBUG_TAG, it.localizedMessage)
            }))
    }
}
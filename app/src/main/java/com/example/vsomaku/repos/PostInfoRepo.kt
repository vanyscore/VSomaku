package com.example.vsomaku.repos

import com.example.vsomaku.ApiHelper
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class PostInfoRepo(private val api : SomakuApi) : BaseRepo() {
    fun loadComments(consumer : Consumer<List<Comment>>, errorConsumer : Consumer<Throwable>, postId : Int) {
        disposable.add(ApiHelper.apiInstance.getComments(postId)
            .map {response ->
                response.body()?.let {
                    it
                }
            }
            .toObservable()
            .flatMapIterable {
                it
            }
            .map {comment ->
                Comment(comment.postId, comment.id,
                    comment.name.let { it[0].toUpperCase() + it.substring(1) },
                    comment.email,
                    comment.text.let { it[0].toUpperCase() + it.replace("\n", "").substring(1) })
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer, errorConsumer))
    }

    fun loadUserInfo(consumer : Consumer<User>, errorConsumer : Consumer<Throwable>, userId : Int) {
        disposable.add(ApiHelper.apiInstance.getUser(userId)
            .map {response ->
                response.body()
            }
            .toObservable()
            .map {
                it[0]
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer, errorConsumer))
    }
}
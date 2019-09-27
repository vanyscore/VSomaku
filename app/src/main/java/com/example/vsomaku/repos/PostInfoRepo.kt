package com.example.vsomaku.repos

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.daos.CommentDao
import com.example.vsomaku.daos.UserDao
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.lang.Exception

class PostInfoRepo(private val userDao : UserDao,
                   private val commentDao : CommentDao,
                   api : SomakuApi) : BaseRepo(api) {
    fun loadComments(consumer : Consumer<List<Comment>>, errorConsumer : Consumer<Throwable>, postId : Int) {
        disposable.add(api.getComments(postId)
            .map {response ->
                if (response.body() != null) response.body()!!
                else throw Exception("null comments")
            }
            .toObservable()
            .doOnNext { comments : List<Comment> ->
                commentDao.insertAll(comments)
            }
            .firstOrError()
            .onErrorResumeNext {
                Log.d(DEBUG_TAG, "Load comments from db in $this")
                commentDao.getCommentsByPostId(postId)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({comments ->
                consumer.accept(comments)
            }, {
                errorConsumer.accept(it)
            }))
    }

    fun loadUserInfo(consumer : Consumer<User>, errorConsumer : Consumer<Throwable>, userId : Int) {
        disposable.add(api.getUsers(userId)
            .map {
                if (it.body() != null) it.body()?.get(0)!!
                else throw Exception("null user")
            }
            .toObservable()
            .doOnNext {user : User ->
                userDao.insert(user)
            }
            .firstOrError()
            .onErrorResumeNext {
                Log.d(DEBUG_TAG,"Load user from db in $this")
                userDao.getUserById(userId)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                consumer.accept(it)
            }, {
                errorConsumer.accept(it)
            }))
    }

//    fun loadUserInfo(consumer: Consumer<Pair<List<Comment>, User>>, errorConsumer : Consumer<Throwable>, postId : Int, userId : Int) {
//        disposable.add(Single.zip(api.getComments(postId), api.getUsers(userId),
//            BiFunction<Response<List<Comment>>, Response<List<User>>, Pair<List<Comment>, User> > { comments, users ->
//                if (comments.body() != null && users.body() != null) {
//                    comments.body()!! to users.body()!![0]
//                } else throw Exception("null comments or users")
//            })
//            .toObservable()
//            .doOnNext {
//                commentDao.insertAll(it.first)
//                userDao.insert(it.second)
//            }
//            .singleOrError()
//            .onErrorResumeNext {
//                "24"
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//
//            }, {
//
//            }))
//    }
}
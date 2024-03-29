package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.PostInfoView
import com.example.vsomaku.repos.PostInfoRepo
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class PostInfoPresenter(private val repo : PostInfoRepo) : MvpPresenter<PostInfoView>() {
    private var reqCount = 0

    fun showPostInfo(post : Post) {
        viewState.bindPostInfo(post)
    }

    fun getComments(postId : Int) {
        repo.loadComments(Consumer {comments ->
            for (comment : Comment in comments) {
                comment.name = comment.name?.let { it[0].toUpperCase() + it.substring(1) }
                comment.text = comment.text?.let { it[0].toUpperCase() + it.replace("\n", "").substring(1) }
            }
            viewState.bindComments(comments)
            if (++reqCount == 2) viewState.showLayout()
        }, Consumer {
            Log.d(DEBUG_TAG, it.localizedMessage)
        }, postId)
    }

    fun getUserInfo(userId : Int) {
        repo.loadUserInfo(Consumer {user ->
            viewState.bindUserInfo(user)
            if (++reqCount == 2) viewState.showLayout()
        }, Consumer {
            Log.d(DEBUG_TAG, it.localizedMessage)
        }, userId)
    }

    override fun onDestroy() {
        repo.destroy()
    }
}
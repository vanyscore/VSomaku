package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.presenters.views.PostsView
import com.example.vsomaku.repos.PostRepo
import io.reactivex.functions.Consumer

class PostsPresenter(private val postRepo: PostRepo) : BasePresenter<PostsView>() {
    fun loadPosts() {
        postRepo.getPosts(Consumer {posts ->
            if (posts != null)
                view?.let {
                    it.bindPosts(posts)
                    it.showLayout()
                }
        }, Consumer {
            Log.d(DEBUG_TAG, it.localizedMessage)
        })
    }

    override fun onDestroy() {
        postRepo.destroy()
    }
}
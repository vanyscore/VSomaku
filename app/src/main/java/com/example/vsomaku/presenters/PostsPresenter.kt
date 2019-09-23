package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.views.PostsView
import com.example.vsomaku.repos.PostRepo
import io.reactivex.Flowable
import io.reactivex.functions.Consumer

class PostsPresenter(private val postRepo: PostRepo) : BasePresenter<PostsView>() {
    fun loadPosts() {
        postRepo.getPosts(Consumer {posts ->
            for (post : Post in posts) {
                post.title = post.title?.let { it[0].toUpperCase() + it.substring(1) }
                post.description = post.description?.let { it[0].toUpperCase() + it.replace("\n", "").substring(1) }
            }

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
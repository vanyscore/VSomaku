package com.example.vsomaku.presenters.views

import com.example.vsomaku.data.Post

interface PostsView : BaseView {
    fun bindPosts(posts : List<Post>)
}
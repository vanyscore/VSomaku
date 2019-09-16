package com.example.vsomaku.presenters.views

import com.example.vsomaku.data.Post

interface PostsView {
    fun bindPosts(posts : List<Post>)
    fun showLayout()
}
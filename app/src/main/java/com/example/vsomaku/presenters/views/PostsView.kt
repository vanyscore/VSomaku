package com.example.vsomaku.presenters.views

import androidx.paging.PagedList
import com.example.vsomaku.data.Post
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface PostsView : MvpView {
    fun bindPosts(posts : List<Post>)
    fun bindPagedList(pagedList: PagedList<Post>)
    fun showLayout()
}
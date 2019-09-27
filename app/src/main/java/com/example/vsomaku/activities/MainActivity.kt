package com.example.vsomaku.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsomaku.App
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.R
import com.example.vsomaku.adapters.PagedListAdapter
import com.example.vsomaku.adapters.PostsAdapter
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.PostsPresenter
import com.example.vsomaku.presenters.views.PostsView
import dagger.Lazy
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), PostsView {

    @Inject
    @InjectPresenter
    lateinit var presenter : PostsPresenter

    @ProvidePresenter
    fun providePresenter() : PostsPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getComponent().injectPostsPresenter(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        presenter.getPagedList()
    }

    override fun bindPosts(posts : List<Post>) {
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = PostsAdapter(this, posts)
    }

    override fun bindPagedList(pagedList: PagedList<Post>) {
        val adapter = PagedListAdapter(this)
        adapter.submitList(pagedList)

        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = adapter
    }

    override fun showLayout() {
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()
    }
}

package com.example.vsomaku.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsomaku.*
import com.example.vsomaku.adapters.PostsAdapter
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.PostsPresenter
import com.example.vsomaku.presenters.views.PostsView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PostsView {

    private val presenter : PostsPresenter = PostsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun bindPosts(posts : List<Post>) {
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = PostsAdapter(this, posts)
    }

    override fun showLayout() {
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }

    override fun hideLayout() {
        progress_bar.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()

        presenter.bindView(this)
        presenter.getPosts()
    }

    override fun onPause() {
        super.onPause()

        presenter.unbindView()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.destroy()
    }
}

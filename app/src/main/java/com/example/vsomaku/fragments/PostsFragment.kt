package com.example.vsomaku.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsomaku.App
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.R
import com.example.vsomaku.adapters.PagedListAdapter
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.PostsPresenter
import com.example.vsomaku.presenters.views.PostsView
import kotlinx.android.synthetic.main.fragment_posts.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class PostsFragment : BaseFragment(), PostsView {

    @Inject
    @InjectPresenter
    lateinit var presenter : PostsPresenter

    @ProvidePresenter
    fun providePresenter() : PostsPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getComponent().injectPostsPresenter(this)

        Log.d(DEBUG_TAG, "PostsFragment created")

        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(DEBUG_TAG, "PostsFragment destroyed")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle(R.string.posts_fragment_title)

        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun bindPosts(posts: List<Post>) {
        // to do after downloading
    }

    override fun bindPagedList(pagedList: PagedList<Post>) {
        val pagedListAdapter : PagedListAdapter

        if (context != null) {
            pagedListAdapter = PagedListAdapter(context!!, router)
            pagedListAdapter.submitList(pagedList)

            recycler_view.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recycler_view.adapter = pagedListAdapter
        }
    }

    override fun showLayout() {
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }
}

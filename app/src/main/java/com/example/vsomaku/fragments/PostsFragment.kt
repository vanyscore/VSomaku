package com.example.vsomaku.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsomaku.App
import com.example.vsomaku.R
import com.example.vsomaku.adapters.PagedListAdapter
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.PostsPresenter
import com.example.vsomaku.presenters.views.PostsView
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject

class PostsFragment : BaseFragment(), PostsView {

    @Inject
    lateinit var presenter : PostsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle(R.string.posts_fragment_title)

        component.injectPostsPresenter(this)

        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onResume() {
        super.onResume()

        presenter.bindView(this)
        presenter.getPagedList()
    }

    override fun onPause() {
        super.onPause()

        presenter.unbindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.onDestroy()
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

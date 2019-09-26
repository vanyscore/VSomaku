package com.example.vsomaku.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsomaku.App
import com.example.vsomaku.DEBUG_TAG

import com.example.vsomaku.R
import com.example.vsomaku.adapters.CommentariesAdapter
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.PostInfoPresenter
import com.example.vsomaku.presenters.views.PostInfoView
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_post_info.*

/**
 * A simple [Fragment] subclass.
 */
class PostInfoFragment : BaseFragment(), PostInfoView {
    @Inject
    lateinit var presenter : PostInfoPresenter

    lateinit var post : Post

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle(R.string.post_info_fragment_title)

        val arguments = arguments
        if (arguments != null)
            post = arguments.getParcelable(POST_KEY)

        component.injectPostInfoPresenter(this)

        Log.d(DEBUG_TAG, "$this view created")

        return inflater.inflate(R.layout.fragment_post_info, container, false)
    }

    override fun onResume() {
        super.onResume()

        presenter.bindView(this)
        presenter.getComments(post.id)
        presenter.getUserInfo(post.userId)
    }

    override fun onPause() {
        super.onPause()

        presenter.unbindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.onDestroy()
    }

    override fun bindComments(comments: List<Comment>) {
        context?.let { recycler_view.adapter = CommentariesAdapter(it, comments) }
        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    @SuppressLint("SetTextI18n")
    override fun bindUserInfo(user: User) {
        tv_user_info.text = "(${user.userName}, ${user.name}, ${user.email})"

        cv_user.setOnClickListener {
            router.startUserInfoFragment(user)
        }
    }

    override fun bindPostInfo(post: Post) {
        tv_title.text = post.title
        tv_description.text = post.description
    }

    override fun showLayout() {
        progress_bar.visibility = View.GONE
        scroll_view.visibility = View.VISIBLE
    }

    companion object {
        const val POST_KEY = "post_key"
    }
}

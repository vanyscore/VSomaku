package com.example.vsomaku.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsomaku.*
import com.example.vsomaku.adapters.CommentariesAdapter
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.PostInfoPresenter
import com.example.vsomaku.presenters.views.PostInfoView
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity(), PostInfoView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val post : Post = intent.getParcelableExtra(POST_KEY)
        val presenter = PostInfoPresenter(this, post)

        presenter.showPostInfo()
        presenter.getUserInfo()
        presenter.getComments()
    }

    override fun bindComments(comments : List<Comment>) {
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = CommentariesAdapter(this, comments)
    }

    override fun bindPostInfo(post : Post) {
        tv_title.text = post.title
        tv_description.text = post.description
    }

    @SuppressLint("SetTextI18n")
    override fun bindUserInfo(user : User) {
        tv_user_info.text = "(${user.name}, ${user.email})"
        cv_user.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra(UserActivity.USER_KEY, user)
            startActivity(intent)
        }
    }

    override fun showLayout() {
        progress_bar.visibility = View.GONE
        scroll_view.visibility = View.VISIBLE
    }

    companion object {
        const val POST_KEY = "post"
    }
}

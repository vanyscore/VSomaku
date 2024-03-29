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
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class PostActivity : MvpAppCompatActivity(), PostInfoView {

    @Inject
    @InjectPresenter
    lateinit var presenter : PostInfoPresenter

    @ProvidePresenter
    fun providePresenter() : PostInfoPresenter = presenter

    lateinit var post : Post

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getComponent().injectPostInfoPresenter(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        post = intent.getParcelableExtra(POST_KEY)
    }

    override fun onResume() {
        super.onResume()

        presenter.getUserInfo(post.userId)
        presenter.getComments(post.id)
        presenter.showPostInfo(post)
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

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()
    }

    companion object {
        const val POST_KEY = "post"
    }
}

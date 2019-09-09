package com.example.vsomaku.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vsomaku.*
import com.example.vsomaku.adapters.CommentariesAdapter
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User

class DetalizationActivity : AppCompatActivity() {
    private lateinit var post : Post
    private var reqCount = 0
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalization)

        recyclerView = findViewById(R.id.recycler_view)
        post = intent.getParcelableExtra(POST_KEY)
        bindPostInfo()

        Thread(ThreadRequest(Handler(commentsCallback), SomakuApi.create().getComments(post.id))).start()
        Thread(ThreadRequest(Handler(userCallback), SomakuApi.create().getUser(post.userId))).start()
    }

    private val commentsCallback = Handler.Callback { msg ->
        val comments : List<Comment> = msg?.data!!.getParcelableArrayList(ThreadRequest.LIST_KEY)
        Log.d(DEBUG_TAG, comments.toString())

        bindComments(comments)

        reqCount++
        if (reqCount == 2) showLayout()

        true
    }

    private val userCallback = Handler.Callback { msg ->
        val users : List<User> = msg?.data!!.getParcelableArrayList(ThreadRequest.LIST_KEY)
        Log.d(DEBUG_TAG, users[0].toString())

        bindUserInfo(users[0])

        reqCount++
        if (reqCount == 2) showLayout()

        true
    }

    private fun bindComments(comments : List<Comment>) {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = CommentariesAdapter(this, comments)
    }

    private fun bindPostInfo() {
        findViewById<TextView>(R.id.tv_title).text = post.title
        findViewById<TextView>(R.id.tv_description).text = post.description
    }

    private fun bindUserInfo(user : User) {
        findViewById<TextView>(R.id.tv_user_info).text = "(${user.name}, ${user.email})"
    }

    private fun showLayout() {
        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        findViewById<ScrollView>(R.id.scroll_view).visibility = View.VISIBLE
    }

    companion object {
        const val POST_KEY = "post"
    }
}

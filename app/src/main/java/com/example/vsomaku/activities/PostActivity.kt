package com.example.vsomaku.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vsomaku.*
import com.example.vsomaku.adapters.CommentariesAdapter
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity() {
    private lateinit var post : Post
    private var reqCount = 0
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        recyclerView = findViewById(R.id.recycler_view)
        post = intent.getParcelableExtra(POST_KEY)
        bindPostInfo()

        ApiHelper.apiInstance.getUser(post.userId).enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d(DEBUG_TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.code() == 200) {
                    val users = response.body()
                    if (users != null)
                        bindUserInfo(users[0])
                }
            }
        })

        ApiHelper.apiInstance.getComments(post.id).enqueue(object : Callback<List<Comment>> {
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d(DEBUG_TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.code() == 200) {
                    val comments = response.body()
                    if (comments != null)
                        bindComments(comments)
                }
            }
        })
    }

    private fun bindComments(comments : List<Comment>) {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = CommentariesAdapter(this, comments)

        reqCount++
        if (reqCount == 2) showLayout()
    }

    private fun bindPostInfo() {
        findViewById<TextView>(R.id.tv_title).text = post.title
        findViewById<TextView>(R.id.tv_description).text = post.description
    }

    private fun bindUserInfo(user : User) {
        findViewById<TextView>(R.id.tv_user_info).text = "(${user.name}, ${user.email})"
        findViewById<CardView>(R.id.cv_user).setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra(UserActivity.USER_KEY, user)
            startActivity(intent)
        }

        reqCount++
        if (reqCount == 2) showLayout()
    }

    private fun showLayout() {
        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        findViewById<ScrollView>(R.id.scroll_view).visibility = View.VISIBLE
    }

    companion object {
        const val POST_KEY = "post"
    }
}

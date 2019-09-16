package com.example.vsomaku.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vsomaku.*
import com.example.vsomaku.adapters.PostsAdapter
import com.example.vsomaku.data.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)

        ApiHelper.apiInstance.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.code() == 200) {
                    val posts = response.body()
                    if (posts != null)
                        bindPosts(posts)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d(DEBUG_TAG, t.localizedMessage)
            }
        })
    }

    private fun bindPosts(posts : List<Post>) {
        for (i : Int in 0 until posts.size) {
            posts[i].description =
                posts[i].description!![0].toUpperCase().toString() + posts[i].description?.replace("\n", " ")?.substring(1)
            posts[i].title = posts[i].title!![0].toUpperCase().toString() + posts[i].title?.substring(1)
        }

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = PostsAdapter(this, posts)

        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}

package com.example.vsomaku.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vsomaku.*
import com.example.vsomaku.adapters.PostsAdapter
import com.example.vsomaku.data.Post

class MainActivity : AppCompatActivity(), Handler.Callback {
    private lateinit var handler : Handler
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = Handler(this)
        recyclerView = findViewById(R.id.recycler_view)

        Thread(
            ThreadRequest(
                handler,
                ApiHelper.apiInstance.getPosts()
            )
        ).start()
    }

    override fun handleMessage(msg: Message): Boolean {
        val posts : List<Post> = msg.data.getParcelableArrayList(ThreadRequest.LIST_KEY)
        for (i : Int in 0 until posts.size) {
            posts[i].description =
                posts[i].description!![0].toUpperCase().toString() + posts[i].description?.replace("\n", " ")?.substring(1)
            posts[i].title = posts[i].title!![0].toUpperCase().toString() + posts[i].title?.substring(1)
        }

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = PostsAdapter(this, posts)

        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        return true
    }
}

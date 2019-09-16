package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.views.PostsView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsPresenter(private val view : PostsView) {
    fun getPosts() {
        ApiHelper.apiInstance.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d(DEBUG_TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val posts = response.body()
                if (posts != null) {
                    for (i : Int in 0 until posts.size) {
                        posts[i].description =
                            posts[i].description!![0].toUpperCase().toString() + posts[i].description?.replace("\n", " ")?.substring(1)
                        posts[i].title = posts[i].title!![0].toUpperCase().toString() + posts[i].title?.substring(1)
                    }

                    view.bindPosts(posts)
                    view.showLayout()
                }
            }
        })
    }
}
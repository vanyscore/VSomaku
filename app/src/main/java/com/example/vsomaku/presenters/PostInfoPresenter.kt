package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.PostInfoView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostInfoPresenter(private val view : PostInfoView, private val post : Post) {
    private var reqCount = 0

    fun showPostInfo() {
        view.bindPostInfo(post)
    }

    fun getComments() {
        ApiHelper.apiInstance.getComments(post.id).enqueue(object : Callback<List<Comment>> {
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d(DEBUG_TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                val comments = response.body()
                if (comments != null) view.bindComments(comments)

                if (++reqCount == 2) view.showLayout()
            }
        })
    }

    fun getUserInfo() {
        ApiHelper.apiInstance.getUser(post.userId).enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d(DEBUG_TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.code() == 200) {
                    val users = response.body()
                    if (users != null) view.bindUserInfo(users[0])

                    if (++reqCount == 2) view.showLayout()
                }
            }
        })
    }
}
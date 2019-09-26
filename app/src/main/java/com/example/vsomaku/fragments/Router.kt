package com.example.vsomaku.fragments

import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User

interface Router {
    fun startPostsFragment()
    fun startPostInfoFragment(post : Post)
    fun startUserInfoFragment(user : User)
}
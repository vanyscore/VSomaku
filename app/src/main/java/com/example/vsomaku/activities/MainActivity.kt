package com.example.vsomaku.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.R
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import com.example.vsomaku.fragments.PostInfoFragment
import com.example.vsomaku.fragments.PostsFragment
import com.example.vsomaku.fragments.Router
import com.example.vsomaku.fragments.UserInfoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.backStackEntryCount == 0)
            startPostsFragment()

        setSupportActionBar(toolbar)

        Log.d(DEBUG_TAG, "Main activity created")
    }

    override fun startPostsFragment() {
        val fragment = PostsFragment()
        fragment.retainInstance = true

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    override fun startPostInfoFragment(post : Post) {
        val fragment = PostInfoFragment()
        val bundle = Bundle()

        fragment.retainInstance = true

        bundle.putParcelable(PostInfoFragment.POST_KEY, post)
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    override fun startUserInfoFragment(user : User) {
        val fragment = UserInfoFragment()
        val bundle = Bundle()

        fragment.retainInstance = true

        bundle.putParcelable(UserInfoFragment.USER_KEY, user)
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0)
            finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(DEBUG_TAG, "Activity [${this.javaClass.name}] destroyed")
        Log.d(DEBUG_TAG, "Fragment stack size : [${supportFragmentManager.backStackEntryCount}]")
    }
}

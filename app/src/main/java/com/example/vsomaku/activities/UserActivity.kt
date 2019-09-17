package com.example.vsomaku.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.R
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.UserInfoPresenter
import com.example.vsomaku.presenters.views.UserView
import kotlinx.android.synthetic.main.activity_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity(), UserView {
    private var presenter : UserInfoPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        if (presenter == null) {
            val user : User = intent.getParcelableExtra(USER_KEY)
            presenter = UserInfoPresenter(user)
        }
    }

     override fun showUserInfo(user : User) {
        tv_name.text = user.name
        tv_user_name.text = user.userName
        tv_email.text = user.email

        tv_address.text = "${user.address.city}, ${user.address.street}, ${user.address.apartment}"
        tv_company_name.text = user.company.name
        tv_company_phrase.text = user.company.phrase
    }

    override fun bindAlbumsInfo(albums: List<Album>, photos : List<Photo>) {
        tv_albums.text = "${albums.size}"
        tv_photos.text =  photos.size.toString()
    }

    override fun showLayout() {
        progress_bar.visibility = View.GONE
        scroll_view.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()

        presenter?.let {
            it.bindView(this)
            it.showUserInfo()
            it.getAlbums()
        }
    }

    override fun onPause() {
        super.onPause()

        presenter?.unbindView()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter?.destroy()
    }

    companion object {
        const val USER_KEY = "user"
    }
}

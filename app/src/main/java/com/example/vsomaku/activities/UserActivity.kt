package com.example.vsomaku.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.vsomaku.App
import com.example.vsomaku.R
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.UserInfoPresenter
import com.example.vsomaku.presenters.views.UserView
import kotlinx.android.synthetic.main.activity_user.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class UserActivity : MvpAppCompatActivity(), UserView {
    @Inject
    @InjectPresenter
    lateinit var presenter : UserInfoPresenter

    @ProvidePresenter
    fun providePresenter() : UserInfoPresenter = presenter

    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getComponent().injectUserInfoPresenter(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        user = intent.getParcelableExtra(USER_KEY)
    }

    override fun onResume() {
        super.onResume()

        presenter.showUserInfo(user)
        presenter.getAlbumsData(user.id)
    }

     override fun showUserInfo(user : User) {
        tv_name.text = user.name
        tv_user_name.text = user.userName
        tv_email.text = user.email
    }

    override fun bindAlbumsInfo(albums: List<Album>, photos : List<Photo>) {
        tv_albums.text = "${albums.size}"
        tv_photos.text =  photos.size.toString()
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
        const val USER_KEY = "user"
    }
}

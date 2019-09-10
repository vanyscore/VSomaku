package com.example.vsomaku.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.R
import com.example.vsomaku.ThreadRequest
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User

class UserActivity : AppCompatActivity() {
    private lateinit var user : User
    private lateinit var albums : List<Album>
    private lateinit var photos : ArrayList<Photo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        user = intent.getParcelableExtra(USER_KEY)
        photos = arrayListOf()

        Thread(ThreadRequest(Handler(albumsCallback), ApiHelper.apiInstance.getAlbums(user.id))).start()
    }

    private val albumsCallback = Handler.Callback {
        run {
            albums = it.data.getParcelableArrayList(ThreadRequest.LIST_KEY)
            bindUserInfo(user, albums)
            val handler = Handler(photosCallback)
            for (album : Album in albums)
                Thread(ThreadRequest(handler, ApiHelper.apiInstance.getPhotos(album.id))).start()
        }
        true
    }

    private val photosCallback = Handler.Callback {
        photos.addAll(it.data.getParcelableArrayList(ThreadRequest.LIST_KEY))
        findViewById<TextView>(R.id.tv_photos).text = "${photos.size}"
        true
    }

    private fun bindUserInfo(user : User, albums : List<Album>) {
        findViewById<TextView>(R.id.tv_name).text = user.name
        findViewById<TextView>(R.id.tv_user_name).text = user.userName
        findViewById<TextView>(R.id.tv_email).text = user.email

        findViewById<TextView>(R.id.tv_address).text = "${user.address.city}, ${user.address.street}, ${user.address.apartment}"
        findViewById<TextView>(R.id.tv_company_name).text = user.company.name
        findViewById<TextView>(R.id.tv_company_phrase).text = user.company.phrase

        findViewById<TextView>(R.id.tv_albums).text = "${albums.size}"

        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        findViewById<ScrollView>(R.id.scroll_view).visibility = View.VISIBLE
    }

    companion object {
        const val USER_KEY = "user"
    }
}

package com.example.vsomaku.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.vsomaku.ApiHelper
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.R
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity() {
    private lateinit var user : User
    private lateinit var photos : ArrayList<Photo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        user = intent.getParcelableExtra(USER_KEY)
        photos = arrayListOf()

        ApiHelper.apiInstance.getAlbums(user.id).enqueue(object : Callback<List<Album>> {
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                Log.d(DEBUG_TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.code() == 200) {
                    val albums = response.body()
                    if (albums != null) {
                        execPhotosCount(albums)
                    }
                }
            }
        })
    }

    private fun bindUserInfo(user : User, albums : List<Album>) {
        findViewById<TextView>(R.id.tv_name).text = user.name
        findViewById<TextView>(R.id.tv_user_name).text = user.userName
        findViewById<TextView>(R.id.tv_email).text = user.email

        findViewById<TextView>(R.id.tv_address).text = "${user.address.city}, ${user.address.street}, ${user.address.apartment}"
        findViewById<TextView>(R.id.tv_company_name).text = user.company.name
        findViewById<TextView>(R.id.tv_company_phrase).text = user.company.phrase

        findViewById<TextView>(R.id.tv_albums).text = "${albums.size}"
        findViewById<TextView>(R.id.tv_photos).text =  photos.size.toString()

        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        findViewById<ScrollView>(R.id.scroll_view).visibility = View.VISIBLE
    }

    private fun execPhotosCount(albums : List<Album>) {
        var albumsCount = 0
        for (album : Album in albums)
            ApiHelper.apiInstance.getPhotos(album.id).enqueue(object : Callback<List<Photo>> {
                override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                    Log.d(DEBUG_TAG, t.localizedMessage)
                }

                override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                    if (response.code() == 200) {
                        val photos = response.body()
                        if (photos != null) {
                            this@UserActivity.photos.addAll(photos)
                            albumsCount++

                            if (albumsCount == albums.size) {
                                bindUserInfo(user, albums)
                                showLayout()
                            }
                        }
                    }
                }
            })
    }

    private fun showLayout() {
        findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        findViewById<ScrollView>(R.id.scroll_view).visibility = View.VISIBLE
    }

    companion object {
        const val USER_KEY = "user"
    }
}

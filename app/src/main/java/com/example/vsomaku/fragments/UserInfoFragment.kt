package com.example.vsomaku.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vsomaku.R
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.UserInfoPresenter
import com.example.vsomaku.presenters.views.UserView
import kotlinx.android.synthetic.main.fragment_user_info.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : BaseFragment(), UserView {

    @Inject
    lateinit var presenter : UserInfoPresenter

    lateinit var user : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setTitle(R.string.user_info_fragment_title)

        if (arguments != null)
            user = arguments!!.getParcelable(USER_KEY)

        component.injectUserInfoPresenter(this)

        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onResume() {
        super.onResume()

        presenter.bindView(this)
        presenter.showUserInfo(user)
        presenter.getAlbumsData(user.id)
    }

    override fun onPause() {
        super.onPause()

        presenter.unbindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.onDestroy()
    }

    override fun showUserInfo(user: User) {
        tv_name.text = user.name
        tv_user_name.text = user.userName
        tv_email.text = user.email
    }

    override fun bindAlbumsInfo(albums: List<Album>, photos: List<Photo>) {
        tv_albums.text = albums.size.toString()
        tv_photos.text = photos.size.toString()
    }

    override fun showLayout() {
        progress_bar.visibility = View.GONE
        scroll_view.visibility = View.VISIBLE
    }

    companion object {
        const val USER_KEY = "user_key"
    }
}

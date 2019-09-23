package com.example.vsomaku.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vsomaku.presenters.BasePresenter
import com.example.vsomaku.presenters.views.BaseView

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getPresenter(): BasePresenter<BaseView>
    abstract fun getView() : BaseView

    override fun onPause() {
        super.onPause()
        getPresenter().unbindView()
    }

    override fun onDestroy() {
        super.onDestroy()

        getPresenter().onDestroy()
    }

    override fun onResume() {
        super.onResume()

        getPresenter().bindView(getView())
    }
}
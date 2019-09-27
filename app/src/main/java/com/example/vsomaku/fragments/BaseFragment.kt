package com.example.vsomaku.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.vsomaku.App
import com.example.vsomaku.activities.MainActivity
import com.example.vsomaku.components.AppComponent
import moxy.MvpAppCompatFragment

open class BaseFragment : MvpAppCompatFragment() {
    protected lateinit var router : Router

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is Router)
            router = context
        else
            throw Exception("${context::class.java.simpleName} must implement Router")
    }
}
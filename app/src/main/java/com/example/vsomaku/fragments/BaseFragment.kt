package com.example.vsomaku.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.vsomaku.App
import com.example.vsomaku.activities.MainActivity
import com.example.vsomaku.components.AppComponent

open class BaseFragment : Fragment() {
    protected lateinit var router : Router
    protected lateinit var component : AppComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is Router){
            router = context
            component = ((context as MainActivity).application as App).component
        }else{
            throw Exception("${context::class.java.simpleName} must implement Router")
        }
    }
}
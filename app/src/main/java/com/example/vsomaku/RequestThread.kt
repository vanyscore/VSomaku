package com.example.vsomaku

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Parcelable
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

abstract class RequestThread<T>(protected val handler : Handler,
                       protected val call : Call<T>) : Runnable, Callback<T> {
    protected val bundle = Bundle()
    protected val message = Message()

    override fun run() {
        call.enqueue(this)
    }

    companion object {
        const val LIST_KEY = "list"
        const val OBJECT_KEY = "object"
    }
}
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

class ThreadRequest<T : List<Parcelable>>(protected val handler : Handler,
                                protected val call : Call<T>) : Runnable, Callback<T> {
    private val bundle = Bundle()
    private val message = Message()

    override fun run() {
        call.enqueue(this)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.d(DEBUG_TAG, t.localizedMessage)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.code() == 200) {
            bundle.putParcelableArrayList(LIST_KEY, response.body() as ArrayList<out Parcelable>)
            message.data = bundle
            handler.sendMessage(message)
        } else {
            Log.d(DEBUG_TAG,"${response.code()}")
        }
    }

    companion object {
        const val LIST_KEY = "list"
        const val OBJECT_KEY = "object"
    }
}
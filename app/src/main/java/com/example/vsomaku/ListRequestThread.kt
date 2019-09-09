package com.example.vsomaku

import android.os.Handler
import android.os.Message
import android.os.Parcelable
import android.util.Log
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class ListRequestThread<T : List<Parcelable>>(handler : Handler, call : Call<T>) : RequestThread<T>(handler, call) {
    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.d(DEBUG_TAG, t.localizedMessage)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        Log.d(DEBUG_TAG, response.toString())

        bundle.putParcelableArrayList(LIST_KEY, response.body() as ArrayList<out Parcelable>)
        message.data = bundle
        handler.sendMessage(message)
    }
}
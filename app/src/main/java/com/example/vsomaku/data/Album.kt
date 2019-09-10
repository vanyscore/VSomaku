package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Album(@SerializedName("id") val id : Int) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(source: Parcel): Album {
            return Album(source.readInt())
        }

        override fun newArray(size: Int): Array<Album> {
            return emptyArray()
        }
    }
}
package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Photo(@SerializedName("id") val id : Int) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(source: Parcel): Photo {
            return Photo(source.readInt())
        }

        override fun newArray(size: Int): Array<Photo> {
            return emptyArray()
        }
    }
}
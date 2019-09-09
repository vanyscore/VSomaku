package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class User(@SerializedName("name") val name : String,
           @SerializedName("email") val email : String) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(email)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(source: Parcel): User {
            return User(source.readString(), source.readString())
        }

        override fun newArray(size: Int): Array<User> {
            return emptyArray()
        }

    }
}
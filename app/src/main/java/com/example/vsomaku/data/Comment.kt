package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Comment(@SerializedName("postId") val postId : Int,
                   @SerializedName("id") val id : Int,
                   @SerializedName("name") val name : String,
                   @SerializedName("email") val email : String,
                   @SerializedName("body") val text : String) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(postId)
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(email)
        dest.writeString(text)
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(source: Parcel): Comment {
            return Comment(
                source.readInt(), source.readInt(),
                source.readString(), source.readString(), source.readString()
            )
        }

        override fun newArray(size: Int): Array<Comment> {
            return emptyArray()
        }

    }
}
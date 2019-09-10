package com.example.vsomaku.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class User(@SerializedName("id") val id : Int,
                @SerializedName("name") val name : String,
                @SerializedName("username") val userName : String,
                @SerializedName("email") val email : String,
                @SerializedName("address") val address : Address,
                @SerializedName("company") val company : Company) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(userName)
        dest.writeString(email)
        dest.writeParcelable(address, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        dest.writeParcelable(company, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(source: Parcel): User {
            return User(source.readInt(),
                source.readString(), source.readString(), source.readString(),
                source.readParcelable(Address::class.java.classLoader),
                source.readParcelable(Company::class.java.classLoader))
        }

        override fun newArray(size: Int): Array<User> {
            return emptyArray()
        }
    }
}
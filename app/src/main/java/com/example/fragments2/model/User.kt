package com.example.fragments2.model

import android.os.Parcel
import android.os.Parcelable
import com.github.javafaker.Avatar

data class User(
    var id: Long,
    var photo: String,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(photo)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(phoneNumber)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

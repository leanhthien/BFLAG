package com.example.minhquan.bflagclient.model

import android.os.Parcel
import android.os.Parcelable
import com.example.minhquan.bflagclient.base.BaseResponse
import com.example.minhquan.bflagclient.utils.KParcelable
import com.example.minhquan.bflagclient.utils.parcelableCreator
import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
        @SerializedName("id") val id: Int?,
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("first_name") val firstName: String?,
        @SerializedName("last_name") val lastName: String?,
        @SerializedName("birth") val birthday: String?,
        @SerializedName("gender") val gender: String?,
        @SerializedName("profile_image") val profileImage: String?) : BaseResponse()

data class Room(
        @SerializedName("id") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("creator") val creator: String?,
        @SerializedName("friends") val listFriends: List<Friend>?,
        @SerializedName("last_message") val lastMessage: Chat?): BaseResponse(), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(Friend),
            parcel.readParcelable(Chat::class.java.classLoader)) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(creator)
        parcel.writeTypedList(listFriends)
        parcel.writeParcelable(lastMessage, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Room> {
        override fun createFromParcel(parcel: Parcel): Room {
            return Room(parcel)
        }

        override fun newArray(size: Int): Array<Room?> {
            return arrayOfNulls(size)
        }
    }
}

data class Friend(
        @SerializedName("email") val email: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("profile_image") val profileImage: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(username)
        parcel.writeString(profileImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Friend> {
        override fun createFromParcel(parcel: Parcel): Friend {
            return Friend(parcel)
        }

        override fun newArray(size: Int): Array<Friend?> {
            return arrayOfNulls(size)
        }
    }
}

data class Chat(
        @SerializedName("time") val time: String?,
        @SerializedName("friend") val friend: Friend?,
        @SerializedName("content") val content: String?,
        @SerializedName("img_url") val imgUrl: String?) : BaseResponse(), Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Friend::class.java.classLoader),
            parcel.readString(),
            parcel.readString()) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(time)
        parcel.writeParcelable(friend, flags)
        parcel.writeString(content)
        parcel.writeString(imgUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chat> {
        override fun createFromParcel(parcel: Parcel): Chat {
            return Chat(parcel)
        }

        override fun newArray(size: Int): Array<Chat?> {
            return arrayOfNulls(size)
        }
    }
}


package com.example.minhquan.bflagclient.model

import android.os.Parcel
import android.os.Parcelable
import com.example.minhquan.bflagclient.base.BaseResponse
import com.example.minhquan.bflagclient.utils.KParcelable
import com.example.minhquan.bflagclient.utils.parcelableCreator
import com.google.gson.annotations.SerializedName

data class SuccessResponse(
        @SerializedName("status") val status: String?,
        @SerializedName("token") val token: String?) : BaseResponse()

data class OnlineResponse(
        @SerializedName("email") val email: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("online") val online: Boolean?) : BaseResponse()

data class ListRoomResponse(
        @SerializedName("rooms") val listRooms: List<Room>?) : BaseResponse(), KParcelable {
    constructor(source: Parcel) : this(
            ArrayList<Room>().apply { source.readList(this, Room::class.java.classLoader) }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeList(listRooms)
    }

    companion object {
        @JvmField val CREATOR = parcelableCreator(::ListRoomResponse)
    }
}

data class ListOnlineUsers(@SerializedName("users") val listUsers: List<Friend>?) : BaseResponse()

data class HistoryChatResponse(@SerializedName("messages") val listChats: List<Chat>?) : BaseResponse()
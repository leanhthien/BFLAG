package com.example.minhquan.bflagclient.model

import com.example.minhquan.bflagclient.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("first_name") val firstName: String?,
        @SerializedName("last_name") val lastName: String?,
        @SerializedName("profile_image") val profileImage: ProfileImage?,
        @SerializedName("list_friends") val listFriends: List<Friend>?,
        @SerializedName("last_rooms") val listRooms: List<Room>?) : BaseResponse()

data class ProfileImage(
        @SerializedName("url") val url: String?,
        @SerializedName("thumb") val thumb: Thumb?,
        @SerializedName("preview") val preview: Preview?
)

data class Thumb(@SerializedName("url") val url: String?)

data class Preview(@SerializedName("url") val url: String?)

data class Room(
        @SerializedName("room_id") val id: Int?,
        @SerializedName("list_friends") val listFriends: List<Friend>?,
        @SerializedName("list_chats") val listChats: List<Chat>?)

data class Friend(
        @SerializedName("email") val email: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("profile_image") val profileImage: String?)

data class Chat(
        @SerializedName("friend") val friend: Friend?,
        @SerializedName("message") val message: Message?,
        @SerializedName("time") val time: String?)

data class Message(
        @SerializedName("content") val content: String?,
        @SerializedName("img_url") val imgUrl: String?)
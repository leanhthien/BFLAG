package com.example.minhquan.bflagclient.model

import com.example.minhquan.bflagclient.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id") val id: Int?,
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("first_name") val firstName: String?,
        @SerializedName("last_name") val lastName: String?,
        @SerializedName("birth") val birthday: String?,
        @SerializedName("gender") val gender: String?,
        @SerializedName("profile_image") val profileImage: String?,
        @SerializedName("list_friends") val listFriends: List<Friend>?,
        @SerializedName("last_rooms") val listRooms: List<Room>?) : BaseResponse()

data class Room(
        @SerializedName("room_id") val id: Int?,
        @SerializedName("name") val name: String?,
        @SerializedName("list_friends") val listFriends: List<Friend>?,
        @SerializedName("list_chats") val listChats: List<Chat>?)

data class Friend(
        @SerializedName("email") val email: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("profile_image") val profileImage: String?)

data class Chat(
        @SerializedName("friend") val friend: Friend?,
        @SerializedName("content") val content: String?,
        @SerializedName("img_url") val imgUrl: String?,
        @SerializedName("time") val time: String?) : BaseResponse()


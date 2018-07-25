package com.example.minhquan.bflagclient.model

import com.google.gson.annotations.SerializedName

data class Chat(val name: String?, val urlAvatar: String?, val message: String?, val type:Int) {
    constructor() : this("", "","", 0)
}
data class ChatResponse(@SerializedName("message") val message: Message?)

data class Message(
        @SerializedName("user")val user: String?,
        @SerializedName("room_id") val roomId: String?,
        @SerializedName("content") val content: String?)
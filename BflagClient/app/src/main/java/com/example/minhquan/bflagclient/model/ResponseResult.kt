package com.example.minhquan.bflagclient.model

import com.example.minhquan.bflagclient.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class SuccessResponse(
        @SerializedName("status") val status: String?,
        @SerializedName("token") val token: String?) : BaseResponse()

data class OnlineResponse(
        @SerializedName("email") val email: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("online") val online: Boolean?): BaseResponse()

data class HistoryChatResponse(val listChats: List<Chat>?): BaseResponse()
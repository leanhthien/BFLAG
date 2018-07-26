package com.example.minhquan.bflagclient.model

import com.example.minhquan.bflagclient.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class SuccessResponse(
        @SerializedName("status") val status: String?,
        @SerializedName("token") val token: String?) : BaseResponse()

data class MessageResponse(
        @SerializedName("user")val user: String?,
        @SerializedName("room_id") val roomId: String?,
        @SerializedName("content") val content: String?)


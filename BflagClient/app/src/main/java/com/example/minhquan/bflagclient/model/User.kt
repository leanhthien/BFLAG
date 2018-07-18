package com.example.minhquan.bflagclient.model

import com.example.minhquan.bflagclient.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("email") val email: String?,
        @SerializedName("password") val password: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("first_name") val firstName: String?,
        @SerializedName("last_name") val lastName: String?) : BaseResponse()


package com.example.minhquan.bflagclient.model

import com.example.minhquan.bflagclient.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class SuccessResponse(@SerializedName("status") val status: String) : BaseResponse()

data class ErrorResponse(@SerializedName("error") val error: String) : BaseResponse()

data class TokenResponse(
        @SerializedName("status") val status: String,
        @SerializedName("token") val token: String) : BaseResponse()


sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Token<out T>(val data: T) : Result<T>()
    data class Error<out T>(val throwable: Throwable) : Result<T>()
}


package com.example.minhquan.bflagclient.base

import com.hosopy.actioncable.Subscription


interface BaseWebSocket<T>: BaseView<T> {

    fun onConnectWebSocketSuccess(subscription: Subscription)

    fun onReceiveLogChatSuccess(message: BaseResponse)

}
package com.example.minhquan.bflagclient.chat

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.Chat
import com.hosopy.actioncable.Subscription


interface ChatContract {

    interface View: BaseView<Presenter> {

        fun onConnectWebSocketSuccess(subscription: Subscription)

        fun onSendLogChatSuccess(data: Chat)
    }

    interface Presenter {

        fun startConnectWebSocket(token: String, room: Int)

        fun startSendLogChat(actionType: String, localChat: MutableList<Chat>, subscription: Subscription)
    }
}
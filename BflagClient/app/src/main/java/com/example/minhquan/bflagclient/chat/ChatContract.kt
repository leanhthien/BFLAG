package com.example.minhquan.bflagclient.chat

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.base.BaseWebSocket
import com.example.minhquan.bflagclient.model.Chat
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.hosopy.actioncable.Subscription
import okhttp3.MultipartBody


interface ChatContract {

    interface View: BaseWebSocket<Presenter> {

        fun onSendImageChatSuccess(result: SuccessResponse)
    }

    interface Presenter {

        fun startSendLogChat(actionType: String, localChat: MutableList<Chat>, subscription: Subscription)

        fun startSendImageChat(token: String, filePart: MultipartBody.Part, roomId: Int)
    }
}

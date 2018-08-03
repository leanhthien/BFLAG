package com.example.minhquan.bflagclient.chat.roomchat

import com.example.minhquan.bflagclient.base.BaseWebSocket
import com.example.minhquan.bflagclient.model.Chat
import com.example.minhquan.bflagclient.model.HistoryChatResponse
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.hosopy.actioncable.Subscription
import okhttp3.MultipartBody


interface ChatRoomContract {

    interface View: BaseWebSocket<Presenter> {

        fun onGetHistoryChatSuccess(result: HistoryChatResponse)

        fun onSendImageChatSuccess(result: SuccessResponse)
    }

    interface Presenter {

        fun startGetHistoryChat(token: String, id: Int, offset: Int)

        fun startSendLogChat(actionType: String, chat: Chat, subscription: Subscription)

        fun startSendImageChat(token: String, filePart: MultipartBody.Part, roomId: Int)
    }
}

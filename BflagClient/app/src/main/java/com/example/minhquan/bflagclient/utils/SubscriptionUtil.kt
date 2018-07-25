package com.example.minhquan.bflagclient.utils

import com.example.minhquan.bflagclient.chat.ChatPresenter
import com.hosopy.actioncable.ActionCable
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
import java.net.URI

class SubscriptionUtil {
    companion object {
        fun buildSubscription(token: String, room: Int) : Subscription {

            // 1. Set up
            val options = Consumer.Options()
            options.connection.headers = mapOf(ChatPresenter.KEY to token)
            val consumer = ActionCable.createConsumer(URI(ChatPresenter.BASE_SERVER_URI), options)

            // 2. Create subscription
            val chatChannel = Channel(ChatPresenter.ROOM_CHANNEL, mapOf(ChatPresenter.ROOM_ID to room))
            return consumer.subscriptions.create(chatChannel)
        }



    }
}
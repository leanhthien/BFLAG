package com.example.minhquan.bflagclient.chat

import android.os.Handler
import android.util.Log
import com.example.minhquan.bflagclient.model.Chat
import com.hosopy.actioncable.ActionCable
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
import java.net.URI
import com.google.gson.GsonBuilder


class ChatPresenter (val view: ChatContract.View): ChatContract.Presenter {

    init {
        this.view.setPresenter(this)
    }

    override fun startConnectWebSocket(token: String, room: Int) {

        // 1. Setup
        var count = 0

        val options = Consumer.Options()
        options.connection.headers = mapOf(TOKEN to token)
        val consumer = ActionCable.createConsumer(URI(BASE_SERVER_URI), options)


        // 2. Create subscription
        val chatChannel = Channel(ROOM_CHANNEL, mapOf(ROOM_ID to room))
        val subscription = consumer.subscriptions.create(chatChannel)

        subscription.onConnected = {
            // Called when the subscription has been successfully completed
            Log.d("Subscription status","Connected")
            view.onConnectWebSocketSuccess(subscription)
        }

        subscription.onRejected = {
            // Called when the subscription is rejected by the server
            Log.d("Subscription status","Rejected")
        }

        subscription.onReceived = { data: Any? ->
            // Called when the subscription receives data from the server

            Log.d("Data return", data.toString())

            val message = GsonBuilder().create().fromJson(data.toString(), Chat::class.java)

            view.onSendLogChatSuccess(message)
        }

        subscription.onDisconnected = {
            // Called when the subscription has been closed
            Log.d("Subscription status","Closed")
        }

        subscription.onFailed = {
            // Called when the subscription encounters any error

            if (count < 4) {
                Handler().postDelayed({
                    count++
                    consumer.connect()
                }, 2000)
            }
            else {
                Log.d("Subscription status", "Error")
                view.showError("Error")
            }

        }

        // 3. Establish connection
        consumer.connect()

    }

    override fun startSendLogChat(actionType: String, localChat: MutableList<Chat>, subscription: Subscription) {

        if (localChat.size > 0 && view.isNetworkConnected() && subscription.onConnected != null) {
            for (i in 0 until localChat.size) {

                subscription.perform(actionType,
                        mapOf(CONTENT to localChat[i].message!!.content))
            }
            localChat.clear()
        }
        else {
            Log.e("Send message","There something went wrong")
        }
    }


    companion object {
        const val BASE_SERVER_URI = "ws://glacial-journey-54219.herokuapp.com/cable"
        const val TOKEN = "Token"
        const val ROOM_CHANNEL = "RoomChannel"
        const val ROOM_ID = "room_id"
        const val ACTION_TYPE = "send_data"
        const val CONTENT = "content"

    }
}

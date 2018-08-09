/**
 * A class with function that create a connection to server by web socket
 */

package com.example.minhquan.bflagclient.utils

import android.util.Log
import com.example.minhquan.bflagclient.base.BaseWebSocket
import com.example.minhquan.bflagclient.model.Chat
import com.example.minhquan.bflagclient.model.OnlineResponse
import com.google.gson.GsonBuilder
import com.hosopy.actioncable.ActionCable
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Consumer
import java.net.URI

const val ONLINE = 0
const val CHAT = 1
const val BASE_SERVER_URI = "ws://glacial-journey-54219.herokuapp.com/cable"
const val ONLINE_CHANNEL = "OnlineChannel"
const val CHAT_CHANNEL = "RoomChannel"
const val ROOM_ID = "room_id"
const val MAX_ATTEMPT = 10


class ChatServerUtil {

    companion object {

        /**
         * Using an action cable library for kotlin to create a connection
         */
        fun startConnectWebSocket(view: BaseWebSocket<*>, token: String, room: Int?, type: Int) {

            // 1. Setup
            var count = 0

            val options = Consumer.Options()
            options.connection.headers = mapOf(TOKEN to token)
            options.connection.reconnection = true
            options.connection.reconnectionMaxAttempts = MAX_ATTEMPT

            val consumer = ActionCable.createConsumer(URI(BASE_SERVER_URI), options)

            // 2. Create subscription
            val chatChannel =  if (type == ONLINE)
                Channel(ONLINE_CHANNEL, mapOf())
            else
                Channel(CHAT_CHANNEL, mapOf(ROOM_ID to room))

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

                val message = GsonBuilder().create()
                        .fromJson(data.toString(),
                                if (type == ONLINE)
                                    OnlineResponse::class.java
                                else
                                    Chat::class.java)

                view.onReceiveLogChatSuccess(message)
            }

            subscription.onDisconnected = {
                // Called when the subscription has been closed
                Log.d("Subscription status","Closed")

            }

            subscription.onFailed = {
                // Called when the subscription encounters any error
                if (count++ > 10) {
                    Log.d("Subscription status", "Error")
                    view.showError("Failed to connect")
                }
            }

            // 3. Establish connection
            consumer.connect()

        }
    }


}
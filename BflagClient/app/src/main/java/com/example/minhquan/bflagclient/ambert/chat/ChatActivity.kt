package com.example.minhquan.bflagclient.ambert.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.minhquan.bflagclient.R
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_chat.*
import com.hosopy.actioncable.ActionCable
import com.hosopy.actioncable.Channel
import com.hosopy.actioncable.Consumer
import java.net.URI


class ChatActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setup()
    }

    private fun setup() {

        btn_begin.setOnClickListener {
            connectWebSocket()
        }
    }

    private fun connectWebSocket() {

        val uri = URI("ws://glacial-journey-54219.herokuapp.com/cable")
        val token = "855186506a72af814d5fc961d8576948"
        val options = Consumer.Options()
        options.connection.headers = mapOf("token" to token)

        val consumer = ActionCable.createConsumer(uri, options)


        // 2. Create subscription
        val chatChannel = Channel("ChatChannel", mapOf("room_" to 1))
        val subscription = consumer.subscriptions.create(chatChannel)

        subscription.onConnected = {
            // Called when the subscription has been successfully completed
            Log.d("Subscription status","Connected")
        }

        subscription.onRejected = {
            // Called when the subscription is rejected by the server
            Log.d("Subscription status","Rejected")
        }

        subscription.onReceived = { data: Any? ->
            // Called when the subscription receives data from the server
            // Possible types...
            when (data) {
                is Int -> { }
                is Long -> { }
                is String -> { text_response.text = data }
                is Double -> { }
                is Boolean -> { }
                is JsonObject -> { }
                is JsonArray -> { }
                else -> { Log.d("Data return","Cannot recognized!") }
            }
        }

        subscription.onDisconnected = {
            // Called when the subscription has been closed
            Log.d("Subscription status","Closed")
        }

        subscription.onFailed = { error ->
            // Called when the subscription encounters any error
            Log.d("Subscription status",error.localizedMessage)
        }

        // 3. Establish connection
        consumer.connect()

        btn_send.setOnClickListener {

            subscription.perform(edt_message.text.toString())
        }

    }
}

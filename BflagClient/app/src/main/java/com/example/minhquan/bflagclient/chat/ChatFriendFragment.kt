package com.example.minhquan.bflagclient.chat

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.ChatAdapter
import com.example.minhquan.bflagclient.model.Chat
import com.example.minhquan.bflagclient.model.ChatResponse
import com.example.minhquan.bflagclient.utils.ConnectivityUtil
import com.example.minhquan.bflagclient.utils.SubscriptionUtil
import com.hosopy.actioncable.Subscription
import kotlinx.android.synthetic.main.fragment_chat_friend.*



class ChatFriendFragment : Fragment(), ChatContract.View {

    private lateinit var presenter: ChatContract.Presenter
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chat: Chat
    private lateinit var historyChat: MutableList<Chat>
    private lateinit var localChat: MutableList<Chat>
    private lateinit var subscription : Subscription

    private val urlAvatar1 = "https://i.pinimg.com/736x/bb/16/5c/bb165c8fcecf107962691450d7505dd3--world-cutest-dog-cutest-dogs.jpg"
    private val urlAvatar2 = "https://d17fnq9dkz9hgj.cloudfront.net/uploads/2018/03/Pomeranian_01-390x203.jpeg"
    private val token = "f8fbf75d65f4f1b674d0f29448635ec1"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat_friend, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyChat = mutableListOf()
        localChat = mutableListOf()

        subscription = SubscriptionUtil.buildSubscription(token, 1)
        ChatPresenter(this, subscription)

        chatAdapter = ChatAdapter(activity!!)
        rv_chat.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_chat.adapter = chatAdapter


        // Set up listener for button
        btn_ChatLeft.setOnClickListener {
            if(!edt_chat.text.isEmpty()) {

                chat = Chat("", urlAvatar1, edt_chat.text.toString(), ChatAdapter.RECEIVER)
                val smoothScroll = chatAdapter.setData(chat)
                rv_chat.smoothScrollToPosition(smoothScroll)
                edt_chat.text = null
                historyChat.add(chat)

                presenter.startSendLogChat( "send_data", localChat)
            }
        }

        // Set up listener for button send message
        img_chat_sender.setOnClickListener {

            if(!edt_chat.text.isEmpty()) {

                chat = Chat("", urlAvatar2, edt_chat.text.toString(), ChatAdapter.SENDER)
                val smoothScroll = chatAdapter.setData(chat)
                rv_chat.smoothScrollToPosition(smoothScroll)
                edt_chat.text = null
                historyChat.add(chat)
                localChat.add(chat)
                // Handle whenever there no network connection

                if (isNetworkConnected())
                    presenter.startSendLogChat("send_data", localChat)

            }
        }

        // Set up listener for button open camera to take a photo
        img_chat_camera.setOnClickListener {

        }

        // Set up listener for button open photo gallery of device
        img_chat_gallery.setOnClickListener {

        }

        // Set up listener for button connect server
        btn_start.setOnClickListener {
            presenter.startConnectWebSocket("f8fbf75d65f4f1b674d0f29448635ec1", 1)

        }
    }


    override fun onConnectWebSocketSuccess() {


    }

    override fun onSendLogChatSuccess(data: ChatResponse) {

        chat = Chat(data.message?.user, urlAvatar2, data.message?.content, ChatAdapter.SENDER)
        val smoothScroll = chatAdapter.setData(chat)
        rv_chat.smoothScrollToPosition(smoothScroll)
        historyChat.add(chat)
    }


    override fun showProgress(isShow: Boolean) {

    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: ChatContract.Presenter) {
        this.presenter = presenter
    }

    override fun onUnknownError(error: String) {
        showError(error)
    }

    override fun onTimeout() {
        showError("Time out")
    }

    override fun onNetworkError() {
        showError("Network Error")
    }

    override fun isNetworkConnected(): Boolean {
        return ConnectivityUtil.isConnected(this.activity!!)
    }


}
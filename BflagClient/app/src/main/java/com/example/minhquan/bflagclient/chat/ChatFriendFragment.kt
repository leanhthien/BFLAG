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
import com.example.minhquan.bflagclient.model.*
import com.example.minhquan.bflagclient.utils.ConnectivityUtil
import com.example.minhquan.bflagclient.utils.PreferenceUtil
import com.hosopy.actioncable.Subscription
import kotlinx.android.synthetic.main.fragment_chat_friend.*
import java.text.SimpleDateFormat
import java.util.*


class ChatFriendFragment : Fragment(), ChatContract.View {

    private lateinit var presenter: ChatContract.Presenter
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var token: String
    private lateinit var user: User
    private lateinit var chat: Chat
    private lateinit var historyChat: MutableList<Chat>
    private lateinit var localChat: MutableList<Chat>
    private lateinit var subscription : Subscription

    //private val urlAvatar1 = "https://i.pinimg.com/736x/bb/16/5c/bb165c8fcecf107962691450d7505dd3--world-cutest-dog-cutest-dogs.jpg"
    //private val urlAvatar2 = "https://d17fnq9dkz9hgj.cloudfront.net/uploads/2018/03/Pomeranian_01-390x203.jpeg"
    //private val token = "54fceb5ff70d8ce9fcb4eff05f8d1415"
    private val room = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat_friend, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sdf = SimpleDateFormat("yyyy:MM:dd HH:mm:ss z", Locale.US)

        ChatPresenter(this)
        historyChat = mutableListOf()
        localChat = mutableListOf()

        chatAdapter = ChatAdapter(activity!!)
        rv_chat.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_chat.adapter = chatAdapter


        //Get current user data
        token = PreferenceUtil(context!!).getToken()
        user = PreferenceUtil(context!!).getUser()

        // Set up listener for button send message
        img_chat_sender.setOnClickListener {

            if(!edt_chat.text.isEmpty()) {

                chat = Chat(
                        Friend(user.email, user.username, user.profileImage?.url),
                        Message( edt_chat.text.toString(), null),
                        sdf.format(Date()))
                val smoothScroll = chatAdapter.setData(chat)
                rv_chat.smoothScrollToPosition(smoothScroll)
                edt_chat.text = null
                localChat.add(chat)

                if (isNetworkConnected())
                    presenter.startSendLogChat(ChatPresenter.ACTION_TYPE, localChat, subscription)

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
            presenter.startConnectWebSocket(token, room)

        }
    }


    override fun onConnectWebSocketSuccess(subscription: Subscription) {

        this.subscription = subscription
    }

    override fun onSendLogChatSuccess(data: Chat) {

        activity!!.runOnUiThread {

            chat = data
            if (data.friend!!.email != PreferenceUtil(context!!).getUser().email) {
                val smoothScroll = chatAdapter.setData(chat)
                rv_chat.smoothScrollToPosition(smoothScroll)
            }
            historyChat.add(chat)
        }

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
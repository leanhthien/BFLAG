package com.example.minhquan.bflagclient.rockleehome.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.rockleeadapter.ChatAdapter
import com.example.minhquan.bflagclient.rockleeutil.Chat
import kotlinx.android.synthetic.main.fragment_chatfriend.*

class ChatFriendFragment : Fragment() {
    private lateinit var chatAdapter: ChatAdapter
    private val urlAvatar1 = "https://i.pinimg.com/736x/bb/16/5c/bb165c8fcecf107962691450d7505dd3--world-cutest-dog-cutest-dogs.jpg"
    private val urlAvatar2 = "https://d17fnq9dkz9hgj.cloudfront.net/uploads/2018/03/Pomeranian_01-390x203.jpeg"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chatfriend, container, false) as ViewGroup
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //...........rv 2
        val demochatL = Chat("", urlAvatar1, "1111", 1)
        val demochatR = Chat("", urlAvatar2, "0000", 0)

        chatAdapter = ChatAdapter(activity!!)
        rv_Chat.adapter = chatAdapter

        btnChatRight.setOnClickListener {
            val a = chatAdapter.setData(demochatR)
            rv_Chat.smoothScrollToPosition(a)
            //Toast.makeText(context, "Button click ${activity?.findViewById<ViewPager>(R.id.vpg_ChatFriend)?.currentItem}", Toast.LENGTH_SHORT).show()
        }
        btn_ChatLeft.setOnClickListener {
            val a = chatAdapter.setData(demochatL)
            rv_Chat.smoothScrollToPosition(a)
        }
    }

}
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
        val list1: MutableList<Chat> = mutableListOf()
        val list2: MutableList<Chat> = mutableListOf()
        creatListChatFriend(list1, "User1", urlAvatar1)//... add list1
        creatListChatFriend(list2, "User2", urlAvatar2)//... add list2

        chatAdapter = ChatAdapter(activity!!)
        rv_Chat.adapter = chatAdapter

        chatAdapter.setData(list1)

        btnTestChat.setOnClickListener {
            chatAdapter.setData(list2)
            Toast.makeText(context, "Button click ${activity?.findViewById<ViewPager>(R.id.vpg_ChatFriend)?.currentItem}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun creatListChatFriend(list: MutableList<Chat>, name: String, urlAvatar: String) {
        list.add(Chat(null, null, null, name, urlAvatar, "1 $name"))
        list.add(Chat(null, null, null, name, urlAvatar, "2 $name"))
        list.add(Chat(name, urlAvatar, "3 $name", null, null, null))
        list.add(Chat(name, urlAvatar, "4 $name", null, null, null))
    }
}
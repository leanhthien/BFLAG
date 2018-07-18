package com.example.minhquan.bflagclient.rocklee.rv.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import kotlinx.android.synthetic.main.fragment_chatfriend.*

class ChatFriendFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chatfriend, container, false) as ViewGroup
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnTestChat.setOnClickListener {
            //activity?.findViewById<ViewPager>(R.id.vpg_ChatFriend)?.currentItem = 1
            Toast.makeText(context,"Button click ${activity?.findViewById<ViewPager>(R.id.vpg_ChatFriend)?.currentItem}",Toast.LENGTH_SHORT).show()
        }
    }
}
package com.example.minhquan.bflagclient.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.minhquan.bflagclient.chat.roomchat.ChatRoomFragment
import android.os.Bundle



class PagerAdapterChatRoom(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private lateinit var fragmentFirst: ChatRoomFragment
    private lateinit var fragmentSecond: ChatRoomFragment
    private lateinit var fragmentThird: ChatRoomFragment
    private lateinit var fragmentFourth: ChatRoomFragment
    private lateinit var fragmentFifth: ChatRoomFragment

    override fun getItem(p0: Int): Fragment? {

        return when(p0) {
           0 -> fragmentFirst
           1 -> fragmentSecond
           2 -> fragmentThird
           3 -> fragmentFourth
           else -> fragmentFifth
       }

    }

    override fun getCount(): Int {
        return 5
    }

    fun setFragment(listRoom: List<Int>) {


        fragmentFirst = ChatRoomFragment()
        fragmentSecond = ChatRoomFragment()
        fragmentThird = ChatRoomFragment()
        fragmentFourth = ChatRoomFragment()
        fragmentFifth = ChatRoomFragment()

        fragmentFirst.setRoom(listRoom[0])
        fragmentSecond.setRoom(listRoom[1])
        fragmentThird.setRoom(listRoom[2])
        fragmentFourth.setRoom(listRoom[3])
        fragmentFifth.setRoom(listRoom[4])

    }

}

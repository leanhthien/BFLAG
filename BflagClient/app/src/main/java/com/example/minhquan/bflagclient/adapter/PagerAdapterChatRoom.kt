package com.example.minhquan.bflagclient.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.minhquan.bflagclient.chat.roomchat.ChatRoomFragment
import android.os.Bundle



class PagerAdapterChatRoom(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    lateinit var fragmentFirst: ChatRoomFragment
    lateinit var fragmentSecond: ChatRoomFragment
    lateinit var fragmentThird: ChatRoomFragment
    lateinit var fragmentFourth: ChatRoomFragment
    lateinit var fragmentFifth: ChatRoomFragment

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

    fun setFragment(room: List<Int>) {
        fragmentFirst = ChatRoomFragment()
        fragmentSecond = ChatRoomFragment()
        fragmentThird = ChatRoomFragment()
        fragmentFourth = ChatRoomFragment()
        fragmentFifth = ChatRoomFragment()

        fragmentFirst.setRoom(room[0])
        fragmentSecond.setRoom(room[1])
        fragmentThird.setRoom(room[2])
        fragmentFourth.setRoom(room[3])
        fragmentFifth.setRoom(room[4])

    }

}
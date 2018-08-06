package com.example.minhquan.bflagclient.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.minhquan.bflagclient.chat.roomchat.ChatRoomFragment


class PagerAdapterChatRoom(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var fragmentFirst: ChatRoomFragment? = null
    private var fragmentSecond: ChatRoomFragment? = null
    private var fragmentThird: ChatRoomFragment? = null
    private var fragmentFourth: ChatRoomFragment? = null
    private var fragmentFifth: ChatRoomFragment? = null
    private var amount = 0

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
        return amount
    }

    fun setFragment(listRoom: List<Int>) {

        amount = listRoom.size
        var  count = amount

        if(count == 5) {
            fragmentFifth = ChatRoomFragment()
            fragmentFifth!!.setRoom(listRoom[4])
            count--
        }

        if (count == 4) {
            fragmentFourth = ChatRoomFragment()
            fragmentFourth!!.setRoom(listRoom[3])
            count--
        }

        if (count == 3) {
            fragmentThird = ChatRoomFragment()
            fragmentThird!!.setRoom(listRoom[2])
            count--
        }

        if (count == 2) {
            fragmentSecond = ChatRoomFragment()
            fragmentSecond!!.setRoom(listRoom[1])
            count--
        }

        if (count == 1) {
            fragmentFirst = ChatRoomFragment()
            fragmentFirst!!.setRoom(listRoom[0])
        }
    }



}

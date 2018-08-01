package com.example.minhquan.bflagclient.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.minhquan.bflagclient.chat.ChatFriendFragment

class PagerAdapterResetPassword(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment? {

        return when (p0) {
            in 0..5 -> ChatFriendFragment()
            else -> ChatFriendFragment()
        }
    }

    override fun getCount(): Int {
        return 5
    }


}
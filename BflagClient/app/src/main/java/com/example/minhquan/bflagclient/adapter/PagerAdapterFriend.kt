package com.example.minhquan.bflagclient.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.minhquan.bflagclient.rockleerv.view.ChatFriendFragment

class PagerAdapterFriend(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment? {
        var fragment: Fragment? = null
        when (p0){
            0 -> fragment = ChatFriendFragment()
            1 -> fragment = ChatFriendFragment()

        }

        return fragment
    }

    override fun getCount(): Int {
        return 2
    }


}
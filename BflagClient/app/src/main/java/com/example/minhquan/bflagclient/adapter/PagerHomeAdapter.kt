package com.example.minhquan.bflagclient.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.home.ActiveFragment
import com.example.minhquan.bflagclient.home.MessagesFragment

class PagerHomeAdapter(var context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getItem(p0: Int): Fragment {
        return if (p0 == 0) {
            MessagesFragment()
        } else {
            ActiveFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> context.getString(R.string.messages)
            1 -> context.getString(R.string.active)
            else -> null
        }
    }
}
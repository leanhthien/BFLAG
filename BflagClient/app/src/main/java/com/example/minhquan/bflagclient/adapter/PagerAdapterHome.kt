package com.example.minhquan.bflagclient.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.minhquan.bflagclient.home.active.ActiveFragment
import com.example.minhquan.bflagclient.home.group.GroupFragment


const val GROUP = "Group"
const val ACTIVE = "Active"

class PagerHomeAdapter(var context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> GroupFragment()
            else -> ActiveFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> GROUP
            else -> ACTIVE
        }
    }



}
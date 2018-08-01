package com.example.minhquan.bflagclient.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.minhquan.bflagclient.resetpassword.code.CodeFragment
import com.example.minhquan.bflagclient.resetpassword.email.EmailFragment
import com.example.minhquan.bflagclient.resetpassword.newpassword.NewPasswordFragment

class PagerAdapterResetPassword(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment? {
        var fragment: Fragment? = null
        when (p0) {
            0 -> fragment = EmailFragment()
            1 -> fragment = CodeFragment()
            2 -> fragment = NewPasswordFragment()
            else -> EmailFragment()
        }
        return fragment
    }

    override fun getCount(): Int {
        return 3
    }


}
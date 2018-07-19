package com.example.minhquan.bflagclient.sign

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.minhquan.bflagclient.PagerAdapter
import com.example.minhquan.bflagclient.R
import kotlinx.android.synthetic.main.activity_signin_signup.*


class SignActivity : FragmentActivity(){

    private var pagerAdapter: PagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_signup)

        pagerAdapter = PagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
    }

}
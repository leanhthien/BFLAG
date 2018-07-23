package com.example.minhquan.bflagclient.sign

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.minhquan.bflagclient.PagerAdapter
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.ambert.capture.CaptureActivity
import com.example.minhquan.bflagclient.ambert.chat.ChatActivity
import com.example.minhquan.bflagclient.ambert.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_signin_signup.*


class SignActivity : FragmentActivity(){

    private var pagerAdapter: PagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_signup)

        pagerAdapter = PagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        //startActivity(Intent(this, ChatActivity::class.java))
    }

}
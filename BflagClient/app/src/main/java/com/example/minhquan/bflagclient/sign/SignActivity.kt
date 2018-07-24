package com.example.minhquan.bflagclient.sign

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.PagerSignAdapter
import com.example.minhquan.bflagclient.ambert.capture.CaptureActivity
import com.example.minhquan.bflagclient.ambert.chat.ChatActivity
import com.example.minhquan.bflagclient.ambert.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign.*


class SignActivity : FragmentActivity(){

    private var pagerAdapter: PagerSignAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        pagerAdapter = PagerSignAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        startActivity(Intent(this, ChatActivity::class.java))
    }

}
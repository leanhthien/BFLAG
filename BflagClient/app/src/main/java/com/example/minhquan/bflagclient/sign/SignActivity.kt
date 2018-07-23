package com.example.minhquan.bflagclient.sign

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.minhquan.bflagclient.adapter.PagerSignAdapter
import com.example.minhquan.bflagclient.R
import kotlinx.android.synthetic.main.activity_sign.*


class SignActivity : FragmentActivity(){

    private var pagerAdapter: PagerSignAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        pagerAdapter = PagerSignAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        //imgHome.bringToFront()
        //startActivity(Intent(this, CaptureActivity::class.java))
    }

}
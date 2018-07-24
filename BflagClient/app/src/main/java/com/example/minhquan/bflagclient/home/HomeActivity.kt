package com.example.minhquan.bflagclient.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.PagerHomeAdapter
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.supportrenderscriptblur.SupportRenderScriptBlur


class HomeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val adapter = PagerHomeAdapter(this,supportFragmentManager)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

        edtSearch.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            val p1 = Pair.create<View,String>(edtSearch,"search")
            val p2 = Pair.create<View,String>(tvLine,"line")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2)
            startActivity(intent, options.toBundle())
        }

    }
}
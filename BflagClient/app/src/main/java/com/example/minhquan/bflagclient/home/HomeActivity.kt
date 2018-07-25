package com.example.minhquan.bflagclient.home

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.PagerHomeAdapter
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.view.View
import android.view.WindowManager

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val adapter = PagerHomeAdapter(this, supportFragmentManager)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

        edtSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            val p1 = Pair.create<View, String>(edtSearch, "search")
            val p2 = Pair.create<View, String>(tvLine, "line")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2)
            startActivity(intent, options.toBundle())
        }

        val window = this.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = getWindow().decorView
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorWhite)

        imgProfile.setImageResource(R.drawable.shin)

        imgProfile.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            val p1 = Pair.create<View, String>(imgProfile, "profile")
            //val p2 = Pair.create<View, String>(tvLine, "line")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1)
            startActivity(intent, options.toBundle())
        }

    }
}
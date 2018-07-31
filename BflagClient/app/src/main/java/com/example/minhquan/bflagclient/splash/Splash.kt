package com.example.minhquan.bflagclient.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R
import kotlinx.android.synthetic.main.activity_splash.*

class Splash: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        animation_splash.setAnimation("world_locations.json")
        animation_splash.speed = 1F
        animation_splash.playAnimation()

    }
}
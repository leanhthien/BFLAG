package com.example.minhquan.bflagclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.minhquan.bflagclient.signup.SignUpActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        startActivity(Intent(this, SignUpActivity::class.java))
    }
}

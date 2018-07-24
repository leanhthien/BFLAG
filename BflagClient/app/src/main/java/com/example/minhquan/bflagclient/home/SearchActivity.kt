package com.example.minhquan.bflagclient.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        tvCancel.setOnClickListener {
            onBackPressed()
        }
    }
}
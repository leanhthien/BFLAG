package com.example.minhquan.bflagclient.resetpassword

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.PagerAdapterResetPassword
import kotlinx.android.synthetic.main.activity_reset_password.*


const val PAGE_LIMIT = 3

class ResetPasswordActivity: AppCompatActivity(){
    private var pagerAdapterResetPassword: PagerAdapterResetPassword? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        setupView()
    }

    private fun setupView() {
        pagerAdapterResetPassword = PagerAdapterResetPassword(supportFragmentManager)
        vpg_reset_password.adapter = pagerAdapterResetPassword
        vpg_reset_password.offscreenPageLimit = PAGE_LIMIT
    }
}
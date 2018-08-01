package com.example.minhquan.bflagclient.resetpassword

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhquan.bflagclient.R
import kotlinx.android.synthetic.main.fragment_resetpassword_code.*

class CodeFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_resetpassword_code, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()

        btn_resetpassword_code.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.vpg_reset_password)?.currentItem = 2
        }
    }

    private fun setupView() {

    }
}
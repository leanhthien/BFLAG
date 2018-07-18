package com.example.minhquan.bflagclient.signin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhquan.bflagclient.R
import kotlinx.android.synthetic.main.fragment_signin.*

class SignInFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false) as ViewGroup
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvSignUp.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.viewPager)?.currentItem=1
        }
    }
}
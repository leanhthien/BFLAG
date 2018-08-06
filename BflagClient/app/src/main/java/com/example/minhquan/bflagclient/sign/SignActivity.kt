package com.example.minhquan.bflagclient.sign

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.adapter.PagerSignAdapter
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.User
import kotlinx.android.synthetic.main.activity_sign.*


class SignActivity : AppCompatActivity() {

    private var pagerAdapter: PagerSignAdapter? = null
    private lateinit var listener: SignListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        pagerAdapter = PagerSignAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        val typeface = Typeface.createFromAsset(assets,"fonts/smile_of_the_ocean.ttf")
        tvBflag.typeface = typeface
    }

    interface SignListener {
        fun onAutoSignUp(user: User)
    }

    fun setListener(listener: SignListener) {
        this.listener = listener
    }

    fun getAutoSignup(user: User) {
        listener.onAutoSignUp(user)
    }
}
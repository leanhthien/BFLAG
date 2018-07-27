package com.example.minhquan.bflagclient.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.adapter.PagerHomeAdapter
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.example.minhquan.bflagclient.home.user.UserActivity
import android.widget.Toast
import com.example.minhquan.bflagclient.chat.ChatActivity
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.sign.SignActivity
import com.example.minhquan.bflagclient.utils.ConnectivityUtil
import com.example.minhquan.bflagclient.utils.PreferenceHelper
import com.example.minhquan.bflagclient.utils.PreferenceHelper.get
import com.example.minhquan.bflagclient.utils.PreferenceUtil

class HomeActivity : AppCompatActivity(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpView()

    }

    private fun setUpView() {
        HomePresenter(this)

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
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)


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

        val token = PreferenceUtil(this).getToken()
        if (token != PreferenceUtil.ERROR)
            presenter.startGetUser(token)


        val height = getHeightNavigation()

        blurNav.layoutParams.height = height
    }

    override fun onGetUserSuccess(result: User) {

        PreferenceUtil(this).setUser(result)
        //startActivity(Intent(this, ChatActivity::class.java))
    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun onUnknownError(error: String) {
        showError(error)
    }

    override fun onTimeout() {
        showError("Time out")
    }

    override fun onNetworkError() {
        showError("Network Error")
    }

    override fun isNetworkConnected(): Boolean {
        return ConnectivityUtil.isConnected(this)

    }

    private fun getHeightNavigation(): Int {

        val resources = this.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }
}
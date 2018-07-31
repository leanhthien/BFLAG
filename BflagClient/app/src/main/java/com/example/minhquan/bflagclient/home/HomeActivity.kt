package com.example.minhquan.bflagclient.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.example.minhquan.bflagclient.ambert.capture.CaptureActivity
import com.example.minhquan.bflagclient.ambert.capture.CapturePresenter
import com.example.minhquan.bflagclient.ambert.signup.SignUpActivity
import com.example.minhquan.bflagclient.chat.ChatActivity
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.*


class HomeActivity : AppCompatActivity(), HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private lateinit var token: String
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpView()

    }

    private fun setUpView() {
        HomePresenter(this)

        // set up adapter for view pager
        val adapter = PagerHomeAdapter(this, supportFragmentManager)
        viewPager.adapter = adapter

        tabLayout.setupWithViewPager(viewPager)

        // animation shared element when click search
        edtSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            val p1 = Pair.create<View, String>(edtSearch, "search")
            val p2 = Pair.create<View, String>(tvLine, "line")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2)
            startActivity(intent, options.toBundle())
        }

        setUpStatusBar()

        // animation shared element when click image profile
        imgProfile.setImageResource(R.drawable.shin)
        imgProfile.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            val p1 = Pair.create<View, String>(imgProfile, "profile")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1)
            startActivity(intent, options.toBundle())
        }

        val token = PreferenceUtil(this).getToken()
        if (token != PreferenceUtil.ERROR)
            presenter.startGetUser(token)

        // get height of navigation bottom bar
        val height = getHeightNavigationBottom()
        // and set for view blur
        blurNav.layoutParams.height = height
    }

    /**
     * Function set up status bar and navigation bottom
     */
    private fun setUpStatusBar() {
        val window = this.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = getWindow().decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // make layout no limit
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        // show and translucent navigation bottom bar
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorWhite)
    }

    /**
     * Function get height of navigation bottom
     */
    private fun getHeightNavigationBottom(): Int {
        val height = getHeightNavigation()

        blurNav.layoutParams.height = height

        val tokenReturn =  SharedPreferenceHelper.getInstance(this).getToken()

        if (tokenReturn != null) {
            token = tokenReturn
            presenter.startGetUser(token)
        }

        val resources = this.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    override fun onGetUserSuccess(result: User) {

        SharedPreferenceHelper.getInstance(this).setUser(result)
        //startActivity(Intent(this, ChatActivity::class.java))
        //startActivity(Intent(this, CaptureActivity::class.java))
        //startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun setPresenter(presenter: HomeContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        count++
        if (count < MAX_RETRY)
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .setAction(RETRY) {
                        presenter.startGetUser(token)
                    }
                    .show()
        else
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .show()
    }

    override fun onUnknownError(error: String) {
        showError(error)
    }

    override fun onTimeout() {
        showError(TIME_OUT)
    }

    override fun onNetworkError() {
        showError(NETWORK_ERROR)
    }

    override fun isNetworkConnected(): Boolean {
        return ConnectivityUtil.isConnected(this)

    }


}
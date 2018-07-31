package com.example.minhquan.bflagclient.home.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.User
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_user.*
import android.view.*
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.utils.*


class UserActivity : AppCompatActivity(),DialogContract.View{

    private lateinit var presenter: DialogContract.Presenter
    private lateinit var token: String

    private var count = 0
    lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUpStatusBar()

       fabMenu.setOnClickListener {
           val intent = Intent(this, DialogActivity::class.java)
           val p1 = Pair.create<View, String>(fabMenu, "menuCircle")
           val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1)

           Blurry.with(this).radius(25).sampling(2).onto(coordinator as ViewGroup)
           startActivity(intent, options.toBundle())
       }

        user =  SharedPreferenceHelper.getInstance(this).getUser()!!

        collapsing.title = user.username
        tvFullname.text = user.firstName + " " + user.lastName
        tvEmail.text = user.email

        btnEditInfo.setOnClickListener {
            startActivity(Intent(this@UserActivity,EditInfoActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        Blurry.delete(coordinator)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setUpStatusBar() {
        val window = this.window

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSignOutSuccess(result: SuccessResponse) {

    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun setPresenter(presenter: DialogContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        count++
        if (count < MAX_RETRY)
            Snackbar.make(this.window.decorView.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .setAction(RETRY) {
                        presenter.startSignOut(token)
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
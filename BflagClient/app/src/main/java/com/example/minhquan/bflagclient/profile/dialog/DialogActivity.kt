package com.example.minhquan.bflagclient.profile.dialog

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.sign.SignActivity
import com.example.minhquan.bflagclient.utils.*
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity(), DialogContract.View {
    private lateinit var presenter: DialogContract.Presenter
    private lateinit var token: String

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_dialog)
        this.setFinishOnTouchOutside(true)

        DialogPresenter(this)

        token =  SharedPreferenceHelper.getInstance(this!!).getToken()!!


        // handle click for item of circle menu
        circleMenu.setOnItemClickListener {
            when (it.id) {
                R.id.home -> {
                    Toast.makeText(this@DialogActivity, "Home!!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@DialogActivity,HomeActivity::class.java))
                }
                R.id.search -> Toast.makeText(this@DialogActivity, "Search!!", Toast.LENGTH_SHORT).show()
                R.id.logout -> {
                    presenter.startSignOut(token)
                }

            }
        }
    }

    override fun onSignOutSuccess(result: SuccessResponse) {
        SharedPreferenceHelper.getInstance(this).removeAll()
        startActivity(Intent(this@DialogActivity,SignActivity::class.java))
        finish()
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
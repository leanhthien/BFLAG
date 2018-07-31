package com.example.minhquan.bflagclient.sign

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.minhquan.bflagclient.adapter.PagerSignAdapter
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.ambert.signup.SignPresenter
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.utils.*
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_sign.*


class SignActivity : AppCompatActivity(), SignContract.View{


    private lateinit var presenter: SignContract.Presenter
    private lateinit var data: JsonObject

    private var pagerAdapter: PagerSignAdapter? = null
    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        SignPresenter(this)

        setupView()
    }

    private fun setupView() {

        pagerAdapter = PagerSignAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        val typeface = Typeface.createFromAsset(assets,"fonts/smile_of_the_ocean.ttf")
        tvBflag.typeface = typeface


        checkSignIn()

    }

    private fun checkSignIn() {

        /*if ((GoogleSignIn.getLastSignedInAccount(this) != null)
                || ( AccessToken.getCurrentAccessToken() != null))
            startActivity(Intent(this, HomeActivity::class.java))*/

        val tokenReturn = SharedPreferenceHelper.getInstance(this).getToken()

        if (tokenReturn != null) {
            data = JsonObject().buildAuthJson(tokenReturn)
            presenter.startAuth(data)
        }
    }

    override fun onAuthSuccess(result: SuccessResponse) {
        if (result.status == "ok")
            startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun setPresenter(presenter: SignContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        count++
        if (count < MAX_RETRY)
            Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .setAction(RETRY) {
                        presenter.startAuth(data)
                    }
                    .setDuration(10000)
                    .setActionTextColor(ContextCompat.getColor(baseContext, R.color.colorPrimary))
                    .show()
        else
            Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
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
/**
 * Activity for Splash screen - The very first screen of app
 */

package com.example.minhquan.bflagclient.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.sign.SignActivity
import com.example.minhquan.bflagclient.utils.*
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_splash.*

const val NOT_YET = 0
const val UN_AUTH = 1
const val AUTH = 2

class Splash : AppCompatActivity(), SplashContract.View {


    private lateinit var presenter: SplashContract.Presenter
    private lateinit var token: String

    private var count = 0
    @Volatile private var check = NOT_YET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SplashPresenter(this)

        playAnimation()
        checkSignIn()
    }

    /**
     * Start animation of splash screen
     */
    private fun playAnimation() {

        animation_splash.setAnimation("world_locations.json")

        animation_splash.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {

                when(check) {
                    AUTH -> {
                        startActivity(Intent(this@Splash, HomeActivity::class.java))
                        finish()
                    }
                    UN_AUTH -> {
                        startActivity(Intent(this@Splash, SignActivity::class.java))
                        finish()
                    }
                    NOT_YET -> animation_splash.playAnimation()
                }

            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}

        })

        animation_splash.playAnimation()
    }

    /**
     *  Check user token for automatically sign in
     */
    private fun checkSignIn() {

        /*if ((GoogleSignIn.getLastSignedInAccount(this) != null)
                || ( AccessToken.getCurrentAccessToken() != null))
            check = AUTH*/

        val tokenReturn = SharedPreferenceHelper.getInstance(this).getToken()

        if (tokenReturn != null) {
            token = tokenReturn
            presenter.startGetUser(token)
        }
        else
            check = UN_AUTH
    }

    override fun onGetUserSuccess(result: User) {
        SharedPreferenceHelper.getInstance(this).setUser(result)
        check = AUTH
    }

    override fun showProgress(isShow: Boolean) {}

    override fun setPresenter(presenter: SplashContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        val error = if (message == TIME_OUT || message == NETWORK_ERROR) message
        else UNKNOWN_ERROR

        count++
        if (count < MAX_RETRY) {
            presenter.startGetUser(token)
        }
        else {
            check = UN_AUTH
            Snackbar.make(this.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
                    .show()
        }

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
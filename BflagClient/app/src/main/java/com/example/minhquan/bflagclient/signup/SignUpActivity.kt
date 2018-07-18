package com.example.minhquan.bflagclient.signup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.TokenResponse
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.ConnectivityUtil
import com.example.minhquan.bflagclient.utils.buildEditJson
import com.example.minhquan.bflagclient.utils.buildSignInJson
import com.example.minhquan.bflagclient.utils.buildSignUpJson
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity(), SignUpContract.View {


    private lateinit var presenter: SignUpContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        SignUpPresenter(this)

        setupView()
    }

    fun setupView() {

        val body = buildSignUpJson("thien@gmail.com", "123456", "anhthien", "Le Anh", "Thien")
        val body_1 = buildSignInJson("thien@gmail.com", "123456")
        val body_2 = buildEditJson(null, null, null)
        val token = "3e4edeb41b928f3ee2dde5bff38e3f8c"

        btn_test.setOnClickListener{
            //presenter.startSignUp(body)
            //presenter.startSignIn(body_1)
            //presenter.startEdit(token, body_2)
            presenter.startSignOut(token)

            /*if (isNetworkConnected())
                Log.d("Network status","Connected")
            else
                Log.d("Network status","No network connection")*/
        }



    }


    /**
     * Functions on request API return successful
     */
    override fun onSignUpSuccess(result: SuccessResponse) {
        Log.d("Sign up return",result.status)
    }

    override fun onSignInSuccess(result: TokenResponse) {
        Log.d("Sign in return",result.token)
    }

    override fun onEditSuccess(result: User) {
        Log.d("Edit return", result.email)
    }

    override fun onSignOutSuccess(result: SuccessResponse) {
        Log.d("Sign out return",result.status)
    }




    override fun showProgress(isShow: Boolean) {

    }

    /**
     * Functions on request API return successful
     */
    override fun showError(message: String) {

        Log.e("Error return", message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: SignUpContract.Presenter) {
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


}

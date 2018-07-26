package com.example.minhquan.bflagclient.sign.signin

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.chat.ChatActivity
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.sign.SignActivity
import com.example.minhquan.bflagclient.utils.ConnectivityUtil
import com.example.minhquan.bflagclient.utils.PreferenceHelper.customPrefs
import com.example.minhquan.bflagclient.utils.buildSignInJson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_signin.*
import com.example.minhquan.bflagclient.utils.PreferenceHelper.set
import com.example.minhquan.bflagclient.utils.PreferenceUtil


class SignInFragment : Fragment(), SignInContract.View {
    private lateinit var presenter: SignInContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false) as ViewGroup
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SignInPresenter(this)

        setupView()

        tvSignUp.setOnClickListener {
            activity?.findViewById<ViewPager>(R.id.viewPager)?.currentItem = 1
        }

    }

    private fun setupView() {

        btnSignIn.setOnClickListener {

            var check = true
            if (TextUtils.isEmpty(edtEmail.text.toString())){
                edtEmail.error = "The value cannot be empty!"
                check = false
            }
            if (TextUtils.isEmpty(edtPassword.text.toString())){
                edtPassword.error = "The value cannot be empty!"
                check = false
            }
            if(check){

                val body = JsonObject().buildSignInJson(edtEmail.text.toString(), edtPassword.text.toString())
                presenter.startSignIn(body)
            }

        }

    }

    override fun onSignInSuccess(result: SuccessResponse) {
        Toast.makeText(context, "Sign in success!!", Toast.LENGTH_SHORT).show()
        Log.d("Sign in return", result.token)

        PreferenceUtil(context!!).setToken(result.token)

        startActivity(Intent(context,HomeActivity::class.java))

    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: SignInContract.Presenter) {
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
        return ConnectivityUtil.isConnected(this.activity!!)
    }
}
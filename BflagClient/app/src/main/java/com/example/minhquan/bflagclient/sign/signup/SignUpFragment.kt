package com.example.minhquan.bflagclient.sign.signup

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.home.HomeActivity
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.utils.ConnectivityUtil
import com.example.minhquan.bflagclient.utils.buildSignUpJson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_signup.*


class SignUpFragment : Fragment(), SignUpContract.View {
    private lateinit var presenter: SignUpContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SignUpPresenter(this)

        setupView()
    }

    private fun setupView() {

        btnSignUp.setOnClickListener {
            var check = true
            if (TextUtils.isEmpty(edtEmailSignUp.text.toString())) {
                edtEmailSignUp.error = "The value cannot be empty!"
                check = false
            }
            if (TextUtils.isEmpty(edtPasswordSignUp.text.toString())) {
                edtPasswordSignUp.error = "The value cannot be empty!"
                check = false
            }
            if (TextUtils.isEmpty(edtUsername.text.toString())) {
                edtUsername.error = "The value cannot be empty!"
                check = false
            }
            if (TextUtils.isEmpty(edtFirstname.text.toString())) {
                edtFirstname.error = "The value cannot be empty!"
                check = false
            }
            if (TextUtils.isEmpty(edtLastname.text.toString())) {
                edtLastname.error = "The value cannot be empty!"
                check = false
            }
            if (check) {
                val body = JsonObject().buildSignUpJson(edtEmailSignUp.text.toString(), edtPasswordSignUp.text.toString(), edtUsername.text.toString(), edtFirstname.text.toString(), edtLastname.text.toString())
                presenter.startSignUp(body)
            }

        }
    }

    override fun onSignUpSuccess(result: SuccessResponse) {
        Toast.makeText(context, "Sign up success!!", Toast.LENGTH_SHORT).show()
        Log.d("Sign up return", result.status)
        startActivity(Intent(context, HomeActivity::class.java))
    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
        return ConnectivityUtil.isConnected(this!!.activity!!)
    }


}
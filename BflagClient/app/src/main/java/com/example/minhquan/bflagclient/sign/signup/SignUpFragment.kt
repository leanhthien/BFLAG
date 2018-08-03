package com.example.minhquan.bflagclient.sign.signup

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.example.minhquan.bflagclient.utils.*
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_signup.*

const val EMPTY_ERROR = "The value cannot be empty!"

class SignUpFragment : Fragment(), SignUpContract.View {

    private lateinit var presenter: SignUpContract.Presenter
    private lateinit var body: JsonObject
    private var count: Int = 0

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
                edtEmailSignUp.error = EMPTY_ERROR
                check = false
            }
            if (TextUtils.isEmpty(edtPasswordSignUp.text.toString())) {
                edtPasswordSignUp.error = EMPTY_ERROR
                check = false
            }
            if (TextUtils.isEmpty(edtUsername.text.toString())) {
                edtUsername.error = EMPTY_ERROR
                check = false
            }
            if (TextUtils.isEmpty(edtFirstname.text.toString())) {
                edtFirstname.error = EMPTY_ERROR
                check = false
            }
            if (TextUtils.isEmpty(edtLastname.text.toString())) {
                edtLastname.error = EMPTY_ERROR
                check = false
            }
            if (check) {
                body = JsonObject().buildSignUpJson(edtEmailSignUp.text.toString(),
                        edtPasswordSignUp.text.toString(),
                        edtUsername.text.toString(),
                        edtFirstname.text.toString(),
                        edtLastname.text.toString())
                presenter.startSignUp(body)
            }

        }
    }

    override fun onSignUpSuccess(result: SuccessResponse) {
        Toast.makeText(context, "Sign up success!!", Toast.LENGTH_SHORT).show()
        Log.d("Sign up return", result.status)

        SharedPreferenceHelper.getInstance(context!!).setToken(result.token)

        startActivity(Intent(context, HomeActivity::class.java))
    }

    override fun showProgress(isShow: Boolean) {

    }

    override fun setPresenter(presenter: SignUpContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        val error = if (message == TIME_OUT || message == NETWORK_ERROR) message else UNKNOWN_ERROR
        count++
        if (count < MAX_RETRY)
            Snackbar.make(activity!!.findViewById(android.R.id.content), error, Snackbar.LENGTH_INDEFINITE)
                .setAction(RETRY) {
                        presenter.startSignUp(body)
                }
                .show()
        else
            Snackbar.make(activity!!.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
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
        return ConnectivityUtil.isConnected(this.activity!!)
    }

}
package com.example.minhquan.bflagclient.profile.changepassword

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.profile.ProfileActivity
import com.example.minhquan.bflagclient.sign.signin.EMPTY_ERROR
import com.example.minhquan.bflagclient.utils.*
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_change_password.*
import okhttp3.RequestBody
import java.util.HashMap

const val INCORRECT_PASSWORD = "Your password was incorrect!"


class ChangePasswordActivity : AppCompatActivity(), ChangePasswordContract.View {

    private lateinit var presenter: ChangePasswordContract.Presenter
    private lateinit var body: JsonObject
    private lateinit var user: User
    private lateinit var token: String

    private var success: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        ChangePasswordPresenter(this)
        user = SharedPreferenceHelper.getInstance(this).getUser()!!
        token = SharedPreferenceHelper.getInstance(this).getToken()!!

        btnSaveChanges.setOnClickListener {
            body = JsonObject().buildSignInJson(user.email.toString(), edtCurrentPassword.text.toString())
            presenter.startSignIn(body)

        }

        btnCancel.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, ProfileActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSignInSuccess(result: SuccessResponse) {
        SharedPreferenceHelper.getInstance(this).setToken(result.token)

        var check = true

        if (TextUtils.isEmpty(edtNewPassword.text.toString())) {
            edtNewPassword.error = EMPTY_ERROR
            check = false
        }
        if (TextUtils.isEmpty(edtRetypeNewpassword.text.toString())) {
            edtRetypeNewpassword.error = EMPTY_ERROR
            check = false
        } else if (edtRetypeNewpassword.text.toString() != edtNewPassword.text.toString()) {
            edtRetypeNewpassword.error = INCORRECT_PASSWORD
            check = false
        }
        if (check) {
            val mapPart = HashMap<String, RequestBody>()
                    .buildRequestBody(null, null, null, edtRetypeNewpassword.text.toString(), null, null)
            token = SharedPreferenceHelper.getInstance(this).getToken()!!

            presenter.startEdit(token, null, mapPart)
        }
        success = true
    }

    override fun showProgress(isShow: Boolean) {
        progressBar.visibility = if (isShow) View.VISIBLE else View.GONE

    }

    override fun setPresenter(presenter: ChangePasswordContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        edtCurrentPassword.error = INCORRECT_PASSWORD

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

    override fun onEditSuccess(result: User) {
        SharedPreferenceHelper.getInstance(this).setUser(result)
        onBackPressed()

    }
}
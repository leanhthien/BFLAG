package com.example.minhquan.bflagclient.resetpassword.newpassword

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.sign.SignActivity
import com.example.minhquan.bflagclient.utils.*
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_resetpassword_code.*
import kotlinx.android.synthetic.main.fragment_resetpassword_email.*
import kotlinx.android.synthetic.main.fragment_resetpassword_newpassword.*


const val EMPTY_ERROR = "The length of password must greater than 6 !"
const val EQUAL_ERROR = "Check for password!"

class NewPasswordFragment : Fragment(), NewPasswordContract.View {
    private lateinit var presenter: NewPasswordContract.Presenter
    private lateinit var body: JsonObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_resetpassword_newpassword, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NewPasswordlPresenter(this)
        setupView()
    }

    private fun setupView() {
        btn_resetpassword_newpass.setOnClickListener {
            //check password 1 == password 2 && password1 < 6
            when {
                edt_resetpassword_newpass1.text.toString().trim().length < 6 -> edt_resetpassword_newpass1.error = EMPTY_ERROR
                edt_resetpassword_newpass1.text.toString() != edt_resetpassword_newpass2.text.toString() -> {
                    edt_resetpassword_newpass1.error = EQUAL_ERROR
                    edt_resetpassword_newpass2.error = EQUAL_ERROR
                }
                else -> {
                    body = JsonObject().buildResetAuthJson( activity!!.edt_resetpassword_email.text.toString(),
                            activity!!.edt_resetpassword_code.text.toString(),edt_resetpassword_newpass2.text.toString())
                    presenter.startResetPassword(body)
                    //Toast.makeText(context, activity!!.edt_resetpassword_email.text.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResetPasswordSuccess(result: SuccessResponse) {
        Toast.makeText(context,"result ${result.status}",Toast.LENGTH_SHORT).show()

        showProgress(false)

        // Start SignActivity and delete data
        startActivity(Intent(this@NewPasswordFragment.activity,SignActivity::class.java))
        this@NewPasswordFragment.activity!!.finish()
        SharedPreferenceHelper.getInstance(context!!).removeAll()
    }

    override fun showProgress(isShow: Boolean) {
        when (isShow) {
            true -> {
                loader_password.visibility = View.VISIBLE
                loader_password.playAnimation()
            }
            false -> {
                loader_password.visibility = View.GONE
                loader_password.pauseAnimation()
            }
        }
    }

    override fun setPresenter(presenter: NewPasswordContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)

        showProgress(false)

        Snackbar.make(activity!!.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
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
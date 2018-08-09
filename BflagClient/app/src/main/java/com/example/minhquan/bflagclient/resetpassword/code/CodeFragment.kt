package com.example.minhquan.bflagclient.resetpassword.code

import android.os.Bundle
import android.support.design.widget.Snackbar

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.resetpassword.newpassword.NewPasswordContract
import com.example.minhquan.bflagclient.utils.*
import com.google.gson.JsonObject

import kotlinx.android.synthetic.main.fragment_resetpassword_code.*
import kotlinx.android.synthetic.main.fragment_resetpassword_email.*

const val EMPTY_ERROR = "The value cannot be empty!"
class CodeFragment : Fragment(), NewPasswordContract.View {
    private lateinit var presenter: NewPasswordContract.Presenter
    private lateinit var body: JsonObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_resetpassword_code, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CodePresenter(this)
        setupView()
    }

    private fun setupView() {
        btn_resetpassword_code.setOnClickListener {
            if(TextUtils.isEmpty(edt_resetpassword_code.text.toString()))
                edt_resetpassword_code.error = EMPTY_ERROR
            else {
                loader_code.visibility = View.VISIBLE
                loader_code.playAnimation()
                body = JsonObject().buildResetAuthJson(activity!!.edt_resetpassword_email.text.toString()
                        ,edt_resetpassword_code.text.toString(), null)
                presenter.startResetPassword(body)
            }
        }
    }

    override fun onResetPasswordSuccess(result: SuccessResponse) {

        showProgress(false)

        activity?.findViewById<ViewPager>(R.id.vpg_reset_password)?.currentItem = 2
    }

    override fun showProgress(isShow: Boolean) {
        when (isShow) {
            true -> {
                loader_code.visibility = View.VISIBLE
                loader_code.playAnimation()
            }
            false -> {
                loader_code.pauseAnimation()
                loader_code.visibility = View.INVISIBLE
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
package com.example.minhquan.bflagclient.resetpassword.email

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
import com.example.minhquan.bflagclient.utils.*
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_resetpassword_code.*
import kotlinx.android.synthetic.main.fragment_resetpassword_email.*

const val EMPTY_ERROR = "The value cannot be empty!"
class EmailFragment : Fragment(), EmailContract.View {

    private lateinit var presenter: EmailContract.Presenter
    private lateinit var body: JsonObject
    var count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_resetpassword_email, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EmailPresenter(this)
        setupView()

    }

    private fun setupView() {
        btn_resetpassword_email.setOnClickListener {
            if(TextUtils.isEmpty(edt_resetpassword_email.text.toString()))
                edt_resetpassword_email.error = EMPTY_ERROR
            else {
                body = JsonObject().buildResetJson(edt_resetpassword_email.text.toString())
                presenter.startResetPassword(body)
            }

        }
    }

    override fun onResetSuccess(result: SuccessResponse) {

        activity?.findViewById<ViewPager>(R.id.vpg_reset_password)?.currentItem = 1
    }

    override fun showProgress(isShow: Boolean) {
        when (isShow) {
            true -> {
                loader_email.visibility = View.VISIBLE
                loader_email.playAnimation()
            }
            false -> {
                loader_email.visibility = View.GONE
                loader_email.pauseAnimation()
            }
        }
    }


    override fun setPresenter(presenter: EmailContract.Presenter) {
        this.presenter = presenter
    }

    override fun showError(message: String) {
        Log.e("Error return", message)
        count++
        if (count < MAX_RETRY)
            Snackbar.make(activity!!.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                    .setAction(RETRY) {
                        body = JsonObject().buildResetJson(edt_resetpassword_email.text.toString())
                        presenter.startResetPassword(body)
                    }
                    .show()
        else
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
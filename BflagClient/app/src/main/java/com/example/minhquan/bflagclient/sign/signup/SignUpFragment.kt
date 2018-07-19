package com.example.minhquan.bflagclient.sign.signup

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.R
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.utils.ConnectivityUtil
import com.example.minhquan.bflagclient.utils.buildSignUpJson
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment : Fragment(), SignUpContract.View{
    private lateinit var presenter: SignUpContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SignUpPresenter(this)

        setupView()
    }

    private fun setupView() {

        btnSignUp.setOnClickListener {
            val body = buildSignUpJson(edtEmailSignUp.text.toString(), edtPasswordSignUp.text.toString(), edtUsername.text.toString(), edtFirstname.text.toString(), edtLastname.text.toString())
            Log.d("Data: ", edtPasswordSignUp.text.toString() + "-"+ edtPasswordSignUp.text.toString()+ "-"+ edtUsername.text.toString())
            presenter.startSignUp(body)
        }
    }

    override fun onSignUpSuccess(result: SuccessResponse) {
        Toast.makeText(context,"Sign up success!!",Toast.LENGTH_SHORT).show()
        Log.d("Sign up return",result.status)
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
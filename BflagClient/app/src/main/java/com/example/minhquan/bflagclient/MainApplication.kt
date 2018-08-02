package com.example.minhquan.bflagclient

import android.app.Application
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhquan.bflagclient.base.BaseResponse
import com.example.minhquan.bflagclient.base.BaseWebSocket
import com.example.minhquan.bflagclient.model.OnlineResponse
import com.example.minhquan.bflagclient.utils.*
import com.hosopy.actioncable.Subscription

class MainApplication: Application(), BaseWebSocket<Any> {

    override fun onCreate() {
        super.onCreate()

        setupView()
    }

    private fun setupView() {
        val tokenReturn = SharedPreferenceHelper.getInstance(this).getToken()
        if (tokenReturn != null)
            ChatServerUtil.startConnectWebSocket(this, tokenReturn, null, ONLINE)
    }

    override fun onConnectWebSocketSuccess(subscription: Subscription) {
        Log.d("Active online", "Success")
    }

    override fun onReceiveLogChatSuccess(message: BaseResponse) {
        val data = message as OnlineResponse
        Log.d("Server response", data.username + "is online")
    }

    override fun showProgress(isShow: Boolean) {}

    override fun setPresenter(presenter: Any) {}

    override fun showError(message: String) {
        Log.e("Error return", message)
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
package com.example.minhquan.bflagclient.base

interface BaseView<T> {

    fun showProgress(isShow: Boolean)

    fun showError(message: String)

    fun setPresenter(presenter: T)

    fun onUnknownError(error: String)

    fun onTimeout()

    fun onNetworkError()

    fun isNetworkConnected(): Boolean


}
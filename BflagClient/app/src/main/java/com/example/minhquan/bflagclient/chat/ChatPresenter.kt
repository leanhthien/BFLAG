package com.example.minhquan.bflagclient.chat

import com.example.minhquan.bflagclient.utils.RetrofitUtil
import io.reactivex.disposables.Disposable

class ChatPresenter(private val view: ChatContract.View) : ChatContract.Presenter {
    //Instance of interface created for Retrofit API calls
    private val service by lazy {
        //Initializing Retrofit stuff
        RetrofitUtil.builderBflagService()
    }

    //Rx Java object that tracks fetching activity
    private var disposable: Disposable? = null

    init {
        this.view.setPresenter(this)
    }
}
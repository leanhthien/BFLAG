package com.example.minhquan.bflagclient.home.active

import com.example.minhquan.bflagclient.model.ListOnlineUsers
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ActivePresenter(private val view: ActiveContract.View): ActiveContract.Presenter {

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

    override fun startGetOnlineUsers(token: String) {
        view.showProgress(true)

        disposable  = service.getOnlineUsers(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<ListOnlineUsers>(view) {
                    override fun onSuccess(result: ListOnlineUsers) {
                        view.onGetOnlineUsersSuccess(result.listUsers!!)
                    }
                })
    }

}
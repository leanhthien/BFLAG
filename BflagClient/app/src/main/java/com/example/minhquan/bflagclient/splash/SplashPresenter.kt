package com.example.minhquan.bflagclient.splash

import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SplashPresenter(private val view: SplashContract.View): SplashContract.Presenter {

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

    override fun startGetUser(token: String) {

        view.showProgress(true)

        disposable  = service.getUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<User>(view) {
                    override fun onSuccess(result: User) {
                        view.onGetUserSuccess(result)
                    }
                })
    }

}
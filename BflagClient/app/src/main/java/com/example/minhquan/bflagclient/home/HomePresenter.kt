package com.example.minhquan.bflagclient.home

import com.example.minhquan.bflagclient.model.Room
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(val view: HomeContract.View): HomeContract.Presenter {

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

        disposable  = service.getUser(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<User>(view) {
                    override fun onSuccess(result: User) {
                        view.onGetUserSuccess(result)
                    }
                })
    }

    override fun startCreateRoom(token: String, body: JsonObject) {

        view.showProgress(true)

        disposable  = service.createRoom(token, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<Room>(view) {
                    override fun onSuccess(result: Room) {
                        view.onCreateRoomSuccess(result)
                    }
                })
    }

}
package com.example.minhquan.bflagclient.search

import com.example.minhquan.bflagclient.model.ListOnlineUsers
import com.example.minhquan.bflagclient.model.ListRoomResponse
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter(private val view: SearchContract.View): SearchContract.Presenter {

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

    override fun startSearchRoom(token: String, query: String) {
        view.showProgress(true)

        disposable  = service.searchRoom(token, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<ListRoomResponse>(view) {
                    override fun onSuccess(result: ListRoomResponse) {
                        view.onSearchRoomSuccess(result.listRooms!!)
                    }
                })
    }

    override fun startSearchUser(token: String, query: String) {
        view.showProgress(true)

        disposable  = service.searchUser(token, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<ListOnlineUsers>(view) {
                    override fun onSuccess(result: ListOnlineUsers) {
                        view.onSearchUserSuccess(result.listUsers!!)
                    }
                })
    }

}
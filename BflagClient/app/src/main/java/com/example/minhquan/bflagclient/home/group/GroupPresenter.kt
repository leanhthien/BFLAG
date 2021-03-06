package com.example.minhquan.bflagclient.home.group

import com.example.minhquan.bflagclient.model.ListRoomResponse
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GroupPresenter(private val view: GroupContract.View): GroupContract.Presenter {

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

    override fun startGetSubscribedRooms(token: String) {

        view.showProgress(true)

        disposable  = service.getSubscribedRooms(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<ListRoomResponse>(view) {
                    override fun onSuccess(result: ListRoomResponse) {
                        view.onGetSubscribedRoomsSuccess(result.listRooms!!)
                    }
                })
    }

}
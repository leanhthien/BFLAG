package com.example.minhquan.bflagclient.resetpassword.code

import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.resetpassword.newpassword.NewPasswordContract
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CodePresenter(private var view: NewPasswordContract.View): NewPasswordContract.Presenter {

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


    override fun startResetPassword(body: JsonObject) {
        view.showProgress(true)

        disposable  = service.getResetPassword(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<SuccessResponse>(view) {
                    override fun onSuccess(result: SuccessResponse) {
                        view.onResetPasswordSuccess(result)
                    }
                })
    }

}
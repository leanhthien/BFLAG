package com.example.minhquan.bflagclient.signup

import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.TokenResponse
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.signup.SignContract
import com.example.minhquan.bflagclient.utils.CallbackWrapper
import com.example.minhquan.bflagclient.utils.RetrofitUtil
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignPresenter(private val view: SignContract.View) : SignContract.Presenter {

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

    override fun startSignUp(body: JsonObject) {
        view.showProgress(true)

        disposable  = service.getSignUp(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<SuccessResponse>(view) {
                    override fun onSuccess(result: SuccessResponse) {
                        view.onSignUpSuccess(result)
                    }
                })

    }

    override fun startSignIn(body: JsonObject) {
        view.showProgress(true)

        disposable  = service.getSignIn(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<TokenResponse>(view) {
                    override fun onSuccess(result: TokenResponse) {
                        view.onSignInSuccess(result)
                    }
                })

    }

    override fun startEdit(token: String, body: JsonObject) {
        view.showProgress(true)

        disposable  = service.getEdit(token, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<User>(view) {
                    override fun onSuccess(result: User) {
                        view.onEditSuccess(result)
                    }
                })

    }

    override fun startSignOut(token: String) {
        view.showProgress(true)

        disposable  = service.getSignOut(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: CallbackWrapper<SuccessResponse>(view) {
                    override fun onSuccess(result: SuccessResponse) {
                        view.onSignOutSuccess(result)
                    }
                })

    }


}



package com.example.minhquan.bflagclient.sign.signin

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.google.gson.JsonObject

interface SignInContract {

    interface View : BaseView<Presenter> {

        fun onSignInSuccess(result: SuccessResponse)

    }

    interface Presenter {

        fun startSignIn(body: JsonObject)

    }
}
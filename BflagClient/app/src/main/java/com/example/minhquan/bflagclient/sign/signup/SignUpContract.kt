package com.example.minhquan.bflagclient.sign.signup

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.google.gson.JsonObject

interface SignUpContract {

    interface View : BaseView<Presenter> {

        fun onSignUpSuccess(result: SuccessResponse)

    }

    interface Presenter {

        fun startSignUp(body: JsonObject)

    }
}
package com.example.minhquan.bflagclient.ambert.signup

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.User
import com.google.gson.JsonObject

interface SignContract {

    interface View : BaseView<Presenter> {

        fun onSignUpSuccess(result: SuccessResponse)

        fun onSignInSuccess(result: SuccessResponse)

        fun onEditSuccess(result: User)

        fun onSignOutSuccess(result: SuccessResponse)

        fun onResetSuccess(result: SuccessResponse)

        fun onResetAuthSuccess(result: SuccessResponse)

    }

    interface Presenter {

        fun startSignUp(body: JsonObject)

        fun startSignIn(body: JsonObject)

        fun startEdit(token: String, body: JsonObject)

        fun startSignOut(token: String)

        fun startResetPassword(body: JsonObject)

        fun startResetAuth(body: JsonObject)

    }
}
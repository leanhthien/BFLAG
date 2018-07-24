package com.example.minhquan.bflagclient.ambert.signup

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.TokenResponse
import com.example.minhquan.bflagclient.model.User
import com.google.gson.JsonObject

interface SignContract {

    interface View : BaseView<Presenter> {

        fun onSignUpSuccess(result: SuccessResponse)

        fun onSignInSuccess(result: TokenResponse)

        fun onEditSuccess(result: User)

        fun onSignOutSuccess(result: SuccessResponse)



    }

    interface Presenter {

        fun startSignUp(body: JsonObject)

        fun startSignIn(body: JsonObject)

        fun startEdit(token: String, body: JsonObject)

        fun startSignOut(token: String)


    }
}
package com.example.minhquan.bflagclient.splash

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.User
import com.google.gson.JsonObject

interface SplashContract {

    interface View: BaseView<Presenter> {

        fun onGetUserSuccess(result: User)
    }

    interface Presenter {

        fun startGetUser(token: String)
    }
}
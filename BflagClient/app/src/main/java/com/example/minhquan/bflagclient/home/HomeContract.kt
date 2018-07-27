package com.example.minhquan.bflagclient.home

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.User

interface HomeContract {
    interface View: BaseView<Presenter> {
        fun onGetUserSuccess(result: User)
    }

    interface Presenter {
        fun startGetUser(token: String)
    }
}
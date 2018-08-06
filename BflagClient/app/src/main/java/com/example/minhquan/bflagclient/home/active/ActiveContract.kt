package com.example.minhquan.bflagclient.home.active

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.Friend

interface ActiveContract {

    interface View: BaseView<Presenter> {

        fun onGetOnlineUsersSuccess(result: List<Friend>)
    }

    interface Presenter {

        fun startGetOnlineUsers(token: String)
    }
}
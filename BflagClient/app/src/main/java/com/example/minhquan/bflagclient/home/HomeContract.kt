package com.example.minhquan.bflagclient.home

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.Room
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.User
import com.google.gson.JsonObject

interface HomeContract {
    interface View: BaseView<Presenter> {

        fun onGetUserSuccess(result: User)

        fun onCreateRoomSuccess(result: Room)

    }

    interface Presenter {

        fun startGetUser(token: String)

        fun startCreateRoom(token: String, body: JsonObject)
    }
}
package com.example.minhquan.bflagclient.search

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.Friend
import com.example.minhquan.bflagclient.model.Room

interface SearchContract {

    interface View: BaseView<Presenter> {

        fun onSearchRoomSuccess(result: List<Room>)

        fun onSearchUserSuccess(result: List<Friend>)
    }

    interface Presenter {

        fun startSearchRoom(token: String, query: String)

        fun startSearchUser(token: String, query: String)
    }
}
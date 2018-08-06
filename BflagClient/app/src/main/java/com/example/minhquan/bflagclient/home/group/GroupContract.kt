package com.example.minhquan.bflagclient.home.group

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.Room

interface GroupContract {

    interface View : BaseView<Presenter> {

        fun onGetSubscribedRoomsSuccess(result: List<Room>)

    }

    interface Presenter {

        fun startGetSubscribedRooms(token: String)

    }
}
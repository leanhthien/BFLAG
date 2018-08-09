package com.example.minhquan.bflagclient.chat

import com.example.minhquan.bflagclient.base.BaseView

interface ChatContract {

    interface View: BaseView<Presenter> {

    }

    interface Presenter {

    }

    interface Listener {
        fun onOpenSetting()
    }
}
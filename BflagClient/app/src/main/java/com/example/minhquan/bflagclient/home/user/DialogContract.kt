package com.example.minhquan.bflagclient.home.user

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.SuccessResponse

interface DialogContract {
    interface View: BaseView<Presenter>{
        fun onSignOutSuccess(result: SuccessResponse)

    }
    interface Presenter{
        fun startSignOut(token: String)
    }
}
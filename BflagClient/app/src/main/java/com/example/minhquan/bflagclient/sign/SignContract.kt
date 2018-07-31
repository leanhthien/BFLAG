package com.example.minhquan.bflagclient.sign

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.google.gson.JsonObject

interface SignContract {

    interface View: BaseView<Presenter> {

        fun onAuthSuccess(result: SuccessResponse)
    }

    interface Presenter {

        fun startAuth(data: JsonObject)
    }
}
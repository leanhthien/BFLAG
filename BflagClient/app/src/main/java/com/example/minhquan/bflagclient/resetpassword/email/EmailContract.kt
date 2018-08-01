package com.example.minhquan.bflagclient.resetpassword.email

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.google.gson.JsonObject

interface EmailContract {
    interface View : BaseView<Presenter>{
        fun onResetSuccess(result: SuccessResponse)
    }
    interface  Presenter {
        fun startResetPassword(body: JsonObject)
    }
}
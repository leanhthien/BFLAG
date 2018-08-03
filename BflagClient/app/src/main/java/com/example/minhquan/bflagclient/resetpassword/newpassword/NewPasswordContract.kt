package com.example.minhquan.bflagclient.resetpassword.newpassword

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.google.gson.JsonObject

interface NewPasswordContract {
    interface View : BaseView<Presenter>{
        fun onResetPasswordSuccess(result: SuccessResponse)
    }
    interface  Presenter {
        fun startResetPassword(body: JsonObject)
    }
}
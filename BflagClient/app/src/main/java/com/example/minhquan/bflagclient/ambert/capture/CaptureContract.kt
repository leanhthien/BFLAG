package com.example.minhquan.bflagclient.ambert.capture

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.User
import com.google.gson.JsonObject
import java.io.File

interface CaptureContract {
    interface View: BaseView<Presenter> {
        fun onEditSuccess(result: User)
    }
    interface Presenter {
        fun startEdit(token: String, file: File, body: JsonObject)
    }
}
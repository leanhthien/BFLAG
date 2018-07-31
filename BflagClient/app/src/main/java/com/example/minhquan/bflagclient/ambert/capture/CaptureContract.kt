package com.example.minhquan.bflagclient.ambert.capture

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.User
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface CaptureContract {
    interface View: BaseView<Presenter> {
        fun onEditSuccess(result: User)
    }
    interface Presenter {
        fun startEdit(token: String, filePart: MultipartBody.Part, mapPart: HashMap<String, RequestBody>)
    }
}
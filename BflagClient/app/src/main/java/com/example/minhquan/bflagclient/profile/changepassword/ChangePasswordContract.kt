package com.example.minhquan.bflagclient.profile.changepassword

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.SuccessResponse
import com.example.minhquan.bflagclient.model.User
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ChangePasswordContract {

    interface View : BaseView<Presenter> {

        fun onSignInSuccess(result: SuccessResponse)

        fun onEditSuccess(result: User)

    }

    interface Presenter {

        fun startSignIn(body: JsonObject)

        fun startEdit(token: String, filePart: MultipartBody.Part?, mapPart: HashMap<String, RequestBody>?)


    }
}
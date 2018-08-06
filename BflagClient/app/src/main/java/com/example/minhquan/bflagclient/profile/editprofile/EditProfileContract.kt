package com.example.minhquan.bflagclient.profile.editprofile

import com.example.minhquan.bflagclient.base.BaseView
import com.example.minhquan.bflagclient.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface EditProfileContract {
    interface View: BaseView<Presenter> {
        fun onEditSuccess(result: User)
    }
    interface Presenter {
        fun startEdit(token: String, filePart: MultipartBody.Part?, mapPart: HashMap<String, RequestBody>?)
    }
}
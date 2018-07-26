package com.example.minhquan.bflagclient.utils

import android.content.Context
import com.example.minhquan.bflagclient.chat.ChatPresenter
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.PreferenceHelper.set
import com.example.minhquan.bflagclient.utils.PreferenceHelper.get
import com.google.gson.GsonBuilder
import com.google.gson.Gson



class PreferenceUtil(context: Context) {
    val instance by lazy {
        PreferenceHelper.customPrefs(context, BFLAG)
    }

    fun setToken(token: String?) {
        instance[ChatPresenter.TOKEN] = token
    }

    fun getToken() : String {
       return instance[ChatPresenter.TOKEN, ERROR]!!
    }

    fun setUser(user: User?) {
        instance[USER] = Gson().toJson(user)
    }

    fun getUser() : User {
        return  Gson().fromJson(instance[USER,ERROR], User::class.java)
    }


    companion object {
        const val BFLAG = "Bflag"
        const val ERROR = "error"
        const val USER = "user"

    }
}
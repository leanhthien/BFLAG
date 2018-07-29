package com.example.minhquan.bflagclient.utils

import android.content.Context
import com.example.minhquan.bflagclient.model.User
import com.example.minhquan.bflagclient.utils.PreferenceHelper.set
import com.example.minhquan.bflagclient.utils.PreferenceHelper.get
import com.google.gson.Gson

const val BFLAG = "Bflag"
const val TOKEN = "token"
const val ERROR = "error"
const val USER = "user"

class PreferenceUtil(context: Context) {
    val instance by lazy {
        PreferenceHelper.customPrefs(context, BFLAG)
    }

    fun setToken(token: String?) {
        instance[TOKEN] = token
    }

    fun getToken() : String? {
       return instance[TOKEN]
    }

    fun setUser(user: User?) {
        instance[USER] = Gson().toJson(user)
    }

    fun getUser() : User? {
        return  Gson().fromJson(instance[USER, ERROR], User::class.java)
    }

}
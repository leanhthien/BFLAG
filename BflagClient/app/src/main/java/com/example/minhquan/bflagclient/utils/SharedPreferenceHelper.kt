package com.example.minhquan.bflagclient.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.minhquan.bflagclient.model.User
import com.google.gson.Gson

class SharedPreferenceHelper private constructor(context: Context) {

    val BFLAG = "BFLAG"
    val TOKEN = "token"
    val USER = "user"

    var instance : SharedPreferences

    init {
        instance = context.getSharedPreferences(BFLAG, Context.MODE_PRIVATE)
    }

    fun setToken(token: String?) {
        instance.edit().putString(TOKEN, token).apply()
    }

    fun getToken() : String? {
        return instance.getString(TOKEN, null)
    }

    fun setUser(user: User?) {
        instance.edit().putString(USER, Gson().toJson(user)).apply()
    }

    fun getUser() : User? {
        return Gson().fromJson(instance.getString(USER, null), User::class.java)
    }

    companion object : SingletonHolder<SharedPreferenceHelper, Context>(::SharedPreferenceHelper)

}
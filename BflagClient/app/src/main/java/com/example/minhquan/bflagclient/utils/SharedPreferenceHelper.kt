package com.example.minhquan.bflagclient.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.minhquan.bflagclient.model.User
import com.google.gson.Gson

class SharedPreferenceHelper private constructor(context: Context) {

    val BFLAG = "BFLAG"
    val TOKEN = "token"
    val TOKEN_GOOGLE = "token_google"
    val TOKEN_FACEBOOK = "token_facebook"
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

    fun setTokenGoogle(token: String?) {
        instance.edit().putString(TOKEN_GOOGLE, token).apply()
    }

    fun getTokenGoogle() : String? {
        return instance.getString(TOKEN_GOOGLE, null)
    }

    fun setTokenFacebook(token: String?) {
        instance.edit().putString(TOKEN_FACEBOOK, token).apply()
    }

    fun getTokenFacebook() : String? {
        return instance.getString(TOKEN_FACEBOOK, null)
    }


    fun setUser(user: User?) {
        instance.edit().putString(USER, Gson().toJson(user)).apply()
    }

    fun getUser() : User? {
        return Gson().fromJson(instance.getString(USER, null), User::class.java)
    }

    fun removeAll() {
        instance.edit().clear().apply()
    }

    companion object : SingletonHolder<SharedPreferenceHelper, Context>(::SharedPreferenceHelper)

}
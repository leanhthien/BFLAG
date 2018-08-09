package com.example.minhquan.bflagclient.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.minhquan.bflagclient.model.User
import com.google.gson.Gson

private const val BFLAG = "BFLAG"
private const val TOKEN_SP = "token"
private const val TOKEN_GOOGLE = "token_google"
private const val TOKEN_FACEBOOK = "token_facebook"
private const val USER = "user"
private const val EMAIL = "email"

class SharedPreferenceHelper private constructor(context: Context) {

    var instance : SharedPreferences = context.getSharedPreferences(BFLAG, Context.MODE_PRIVATE)

    fun setToken(token: String?) {
        instance.edit().putString(TOKEN_SP, token).apply()
    }

    fun getToken() : String? {
        return instance.getString(TOKEN_SP, null)
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

    fun setEmail(token: String?) {
        instance.edit().putString(EMAIL, token).apply()
    }

    fun getEmail() : String? {
        return instance.getString(EMAIL, null)
    }

    fun removeAll() {
        instance.edit().clear().apply()
    }

    companion object : SingletonHolder<SharedPreferenceHelper, Context>(::SharedPreferenceHelper)

}
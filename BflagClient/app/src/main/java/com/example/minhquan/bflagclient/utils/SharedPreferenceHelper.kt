package com.example.minhquan.bflagclient.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper private constructor() {


    init {

    }

    companion object {

        private val mInstance: SharedPreferenceHelper = SharedPreferenceHelper()

        @Synchronized
        fun getInstance(): SharedPreferenceHelper {
            return mInstance
        }
    }


}
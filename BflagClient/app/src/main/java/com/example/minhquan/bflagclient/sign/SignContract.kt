package com.example.minhquan.bflagclient.sign

import com.example.minhquan.bflagclient.model.User

interface SignContract {

    interface Listener {
        fun onAutoSignUp(user: User)
    }

}
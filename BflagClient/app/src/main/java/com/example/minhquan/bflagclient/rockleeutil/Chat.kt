package com.example.minhquan.bflagclient.rockleeutil

data class Chat(val name: String?, val urlAvatar: String?, val message: String?, val type:Int) {
    constructor() : this("", "","", 0)
}
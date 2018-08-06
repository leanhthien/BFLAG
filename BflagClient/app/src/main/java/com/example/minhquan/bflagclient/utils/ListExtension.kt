package com.example.minhquan.bflagclient.utils

import com.example.minhquan.bflagclient.model.Room


fun List<Room>.sort() : List<Room> {
    val emptyList = listOf(Room(null, "empty", null, null, null))
    val nullList = filter { it -> it.lastMessage == null }.sortedBy { it -> it.name }
    val nonNullList = filter { it -> it.lastMessage != null }.sortedBy { it -> it.lastMessage!!.time }.asReversed()
    return emptyList + nonNullList + nullList
}
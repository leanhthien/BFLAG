package com.example.minhquan.bflagclient.utils

import com.example.minhquan.bflagclient.model.Room

const val HOME = -1
const val SEARCH = -2

/**
 * Return a modified list for recycler view
 * Add empty item and sort by date and name
 */
fun List<Room>.sort(type: Int?) : List<Room> {
    val emptyList = listOf(Room(type, "empty", null, null, null))
    val nullList = filter { it -> it.lastMessage == null }.sortedBy { it -> it.name }
    val nonNullList = filter { it -> it.lastMessage != null }.sortedBy { it -> it.lastMessage!!.time }.asReversed()

    return  emptyList + nonNullList + nullList
}
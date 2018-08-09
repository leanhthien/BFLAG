/**
 * Copyright (c) 2017-present, Facebook, Inc. All rights reserved.
 * Extension function for creating a timeline
 */

package com.example.minhquan.bflagclient.utils

import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

fun String.makePrettyDate(): String {

    val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
    val pos = ParsePosition(0)
    val then = formatter.parse(this, pos).time
    val now = Date().time

    val seconds = (now - then - 7*3600000) / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    var friendly: String?
    val num: Long
    when {

        days > 28 -> {
            return this.makeFullDate()
        }
        days > 0 -> {
            num = days
            friendly = days.toString() + " day"
        }
        hours > 0 -> {
            num = hours
            friendly = hours.toString() + " hour"
        }
        minutes > 0 -> {
            num = minutes
            friendly = minutes.toString() + " minute"
        }
        else -> {
            num = seconds
            friendly = seconds.toString() + " second"
        }
    }
    if (num > 1) {
        friendly += "s"
    }
    return "$friendly ago"
}

fun String.makeFullDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
    val pos = ParsePosition(0)
    val then = formatter.parse(this, pos)

    return DateFormat.getDateInstance().format(then)
}
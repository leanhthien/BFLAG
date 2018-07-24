/**
 * Copyright (c) 2017-present, Facebook, Inc. All rights reserved.
 * <p>
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 * <p>
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.minhquan.bflagclient.utils

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


fun String.makePrettyDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.US)
    val pos = ParsePosition(0)
    val then = formatter.parse(this, pos).time
    val now = Date().time

    val seconds = (now - then) / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    var friendly: String?
    val num: Long
    when {
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
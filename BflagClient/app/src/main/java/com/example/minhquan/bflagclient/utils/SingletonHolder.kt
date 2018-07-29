/**
 * Copyright (c) 2017 by Christophe Beyls. All detail of this file is based on article:
 * https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 */

package com.example.minhquan.bflagclient.utils

open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}
package com.example.minhquan.bflagclient.demoroom.user

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users")
class User {

    @PrimaryKey
    private val uid: Int = 0

    @ColumnInfo(name = "first_name")
    private val firstName: String? = null

    @ColumnInfo(name = "last_name")
    private val lastName: String? = null

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
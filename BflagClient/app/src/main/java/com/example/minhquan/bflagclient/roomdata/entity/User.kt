package com.example.minhquan.bflagclient.roomdata.entity

import android.arch.persistence.room.*
import org.jetbrains.annotations.NotNull

@Entity(tableName = "user")
class User {
    @PrimaryKey()
    @NotNull
    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "password")
    @NotNull
    var password: String = ""

    @ColumnInfo(name = "username")
    var username: String? = null

    @ColumnInfo(name = "first_name")
    var firstname: String? = null

    @ColumnInfo(name = "last_name")
    var lastname: String? = null

    @ColumnInfo(name = "profile_image")
    var profileimage: String? = null
}
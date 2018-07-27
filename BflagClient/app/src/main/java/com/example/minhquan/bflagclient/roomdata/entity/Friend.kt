package com.example.minhquan.bflagclient.roomdata.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "friend")
class Friend{
    @PrimaryKey()
    @NotNull
    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "profile_image")
    var profileimage: String? = null

}

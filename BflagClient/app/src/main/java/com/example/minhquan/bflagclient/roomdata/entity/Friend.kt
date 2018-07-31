package com.example.minhquan.bflagclient.roomdata.entity

import android.arch.persistence.room.*
import org.jetbrains.annotations.NotNull


@Entity(tableName = "friend",
        foreignKeys = arrayOf(ForeignKey(entity = User::class,
                parentColumns = arrayOf("email"),
                childColumns =  arrayOf("email_user"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)))
class Friend {
    @PrimaryKey()
    @NotNull
    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "email_user")
    var emailuser: String= ""

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "profile_image")
    var profileimage: String? = null

}

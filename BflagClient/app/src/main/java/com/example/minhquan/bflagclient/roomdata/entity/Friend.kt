package com.example.minhquan.bflagclient.roomdata.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import android.arch.persistence.room.ForeignKey.CASCADE


/*@Entity(foreignKeys = arrayOf(ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("emailUser"),
        childColumns = arrayOf("email"),
        onDelete = CASCADE)))*/
@Entity(tableName = "friend")
class Friend{
    @PrimaryKey()
    @NotNull
    var email: String = ""

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "profile_image")
    var profileimage: String? = null

    @ColumnInfo(name = "email_user")
    var emailUser: String? = null
}

package com.example.minhquan.bflagclient.roomdata.entity

import android.arch.persistence.room.*
import org.jetbrains.annotations.NotNull


@Entity(tableName = "room_chat",
        foreignKeys = arrayOf(ForeignKey(entity = User::class,
                parentColumns = arrayOf("email"),
                childColumns =  arrayOf("email_user"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)))
class RoomChat {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "email_user")
    var emailuser: String= ""
}

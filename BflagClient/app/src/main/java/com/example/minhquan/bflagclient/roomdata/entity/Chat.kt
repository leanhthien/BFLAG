package com.example.minhquan.bflagclient.roomdata.entity

import android.arch.persistence.room.*
import org.jetbrains.annotations.NotNull


@Entity(tableName = "chat",
        foreignKeys = arrayOf(ForeignKey(entity = RoomChat::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("room_id"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)))
class Chat {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "room_id")
    @NotNull
    var roomid: Int = 0

    @ColumnInfo(name = "time")
    var time: String? = null

    //......... friend chat
    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "user_name")
    var username: String? = null

    @ColumnInfo(name = "profile_image")
    var profileimage: String? = null
    // message chat
    @ColumnInfo(name = "content")
    var content: String? = null

    @ColumnInfo(name = "img_url")
    var imgurl: String? = null

}

package com.example.minhquan.bflagclient.roomdata.entity

import android.arch.persistence.room.*
import org.jetbrains.annotations.NotNull


@Entity(tableName = "friends_room",
        foreignKeys = arrayOf(ForeignKey(entity = RoomChat::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("room_id"),
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)))
class FriendInRoom {
    @PrimaryKey()
    @NotNull
    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "room_id")
    var roomid: Int? = 0

    @ColumnInfo(name = "user_name")
    var username: String? = null

    @ColumnInfo(name = "profile_image")
    var profileimage: String? = null

}

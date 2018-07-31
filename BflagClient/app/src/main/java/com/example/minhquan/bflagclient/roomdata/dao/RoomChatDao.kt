package com.example.minhquan.bflagclient.roomdata.dao

import android.arch.persistence.room.*
import com.example.minhquan.bflagclient.roomdata.entity.Friend
import com.example.minhquan.bflagclient.roomdata.entity.RoomChat
import com.example.minhquan.bflagclient.roomdata.entity.User


@Dao
interface RoomChatDao {

    @get:Query("SELECT * FROM room_chat")
    val all: List<RoomChat>

    @Query("DELETE FROM room_chat")
    fun deleteAll()

    @Delete
    fun delete(roomChat: RoomChat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg roomChat: RoomChat)

    @Query("SELECT * FROM room_chat WHERE email_user = :emailUser")
    fun findRoomForUser(emailUser: String): List<RoomChat>

}

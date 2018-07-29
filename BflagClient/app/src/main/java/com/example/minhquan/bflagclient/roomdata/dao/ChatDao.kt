package com.example.minhquan.bflagclient.roomdata.dao

import android.arch.persistence.room.*
import com.example.minhquan.bflagclient.roomdata.entity.Chat


@Dao
interface ChatDao {

    @get:Query("SELECT * FROM chat")
    val all: List<Chat>

    @Query("DELETE FROM chat")
    fun deleteAll()

    @Delete
    fun delete(chat: Chat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg chat: Chat)

    @Query("SELECT * FROM chat WHERE room_id = :roomId")
    fun getChatForRoom(roomId: Int?): List<Chat>

}

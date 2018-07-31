package com.example.minhquan.bflagclient.roomdata.dao

import android.arch.persistence.room.*
import com.example.minhquan.bflagclient.roomdata.entity.FriendInRoom


@Dao
interface FriendInRoomDao {

    @get:Query("SELECT * FROM friends_room")
    val all: List<FriendInRoom>

    @Query("DELETE FROM friends_room")
    fun deleteAll()

    @Delete
    fun delete(friendInRoom: FriendInRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg friendInRoom: FriendInRoom)

    @Query("SELECT * FROM friends_room WHERE room_id = :roomId")
    fun getFriendForRoom(roomId: Int?): List<FriendInRoom>

}
